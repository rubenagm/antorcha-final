package com.mx.antorcha.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Conexion.ConexionLogin;
import com.mx.antorcha.OtrasFunciones.CalculoFechas;
import com.mx.antorcha.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private CallbackManager callbackManager;
    private EditText editTextCorreo;
    private EditText editTextPass;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        //se inicializan los campos de texto
        editTextCorreo = (EditText) findViewById(R.id.login_campo_correo);
        editTextPass = (EditText) findViewById(R.id.login_campo_pass);

        //Se agrega el callback
        callbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_facebook_login);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("Login Facebook", loginResult.toString());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.v("LoginActivity", response.toString());
                                String respuesta = response.toString().replace("{Response:", "");
                                respuesta = respuesta.replace("responseCode: 200, graphObject:", "");
                                respuesta = respuesta.toString().replace(", error: null}", "");
                                try {
                                    JSONObject jsonObject = new JSONObject(respuesta);
                                    String idFacebook = jsonObject.getString("id");
                                    String nombre = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    String genero = "N";
                                    String fechaNacimiento = "0000-00-00";

                                    if (jsonObject.has("gender")) {
                                        genero = jsonObject.getString("gender");
                                    }

                                    if (jsonObject.has("birthday")) {
                                        fechaNacimiento = jsonObject.getString("birthday");
                                        fechaNacimiento = CalculoFechas.cambiarFormatoFacebook(fechaNacimiento);
                                    }

                                    ConexionLogin conexionLogin = new ConexionLogin(idFacebook, Login.this);
                                    conexionLogin.setNombre(nombre);
                                    conexionLogin.setCorreo(email);
                                    conexionLogin.setGenero(genero);
                                    conexionLogin.setFechaNacimiento(fechaNacimiento);
                                    conexionLogin.login();

                                    conexionLogin.execute();

                                    LoginManager.getInstance().logOut();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("Login Facebook", "Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Login Facebook", error.toString());
            }
        });

        //Se carga el boton de inicio de sesión
        ImageView imageViewInicioSesion = (ImageView) findViewById(R.id.login_boton_inicio_sesion);
        ImageView imageViewAtras = (ImageView) findViewById(R.id.login_barra_atras);

        //Se carga la imagen
        AdaptadorSVG.mostrarImagen(imageViewInicioSesion, this, R.raw.boton_iniciar_sesion);
        AdaptadorSVG.mostrarImagen(imageViewAtras, this, R.raw.icono_regresar);

        //el on click de regresar
        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //el click en el inicio de sesión
        imageViewInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionLogin conexionLogin = new ConexionLogin(Login.this,
                        editTextCorreo.getText().toString(),
                        editTextPass.getText().toString());

                conexionLogin.login();
            }
        });

        //Login de google

        //Cuando se da click en el login de google
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //Se configura el archivo de opciones de google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //La api que realiza la conexión
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Login.this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Se diseña el botón
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                String nombre = acct.getDisplayName();
                String id = acct.getId();
                String correo = acct.getEmail();

                ConexionLogin conexionLogin = new ConexionLogin(Login.this, id);
                conexionLogin.setNombre(nombre);
                conexionLogin.setCorreo(correo);
                conexionLogin.setGenero("N");
                conexionLogin.setFechaNacimiento("1992-02-02");

                conexionLogin.login();
            } else {
                Toast.makeText(Login.this, "Hubo un error al acceder a tu cuenta de google", Toast.LENGTH_SHORT).show();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }

}
package com.example.duoihinhbatchu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duoihinhbatchu.adapter.NguoiChoiAdapter;
import com.example.duoihinhbatchu.model.ChoiGameModel;
import com.example.duoihinhbatchu.object.NguoiChoi;
import com.example.duoihinhbatchu.object.NguoiDung;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BangXepHang extends AppCompatActivity {

    TabHost th;
    NguoiDung nguoiDung = new NguoiDung();

    ImageView img_avt;
    TextView tv_name, tv_email, tv_ruby, tv_thanhtich;
    Button btn_Logout;

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton btn_googleLogin;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    String DATABASE_NAME = "DuoiHinhBatChu.db";
    SQLiteDatabase database;

    ListView lv;
    ArrayList<NguoiChoi> ds_NguoiChoi = new ArrayList<>();
    NguoiChoiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_xep_hang);
        Init();
        Events();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1030650640689-gd0437jmloc9jleivlmrptipi0vo7p1i.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void Events() {
        img_avt.setVisibility(View.GONE);
        tv_email.setVisibility(View.GONE);
        tv_name.setVisibility(View.GONE);
        tv_thanhtich.setVisibility(View.GONE);
        tv_ruby.setVisibility(View.GONE);
        btn_Logout.setVisibility(View.GONE);

        btn_googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mAuth.signOut();
                            Toast.makeText(BangXepHang.this, "Logout successful", Toast.LENGTH_SHORT).show();

                            img_avt.setVisibility(View.GONE);
                            tv_email.setVisibility(View.GONE);
                            tv_name.setVisibility(View.GONE);
                            tv_thanhtich.setVisibility(View.GONE);
                            tv_ruby.setVisibility(View.GONE);
                            btn_Logout.setVisibility(View.GONE);

                            btn_googleLogin.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ds_NguoiChoi.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    NguoiChoi nc = ds.getValue(NguoiChoi.class);
                    ds_NguoiChoi.add(nc);
                }
                //Sắp xếp theo chiều giảm dần thành tích
                for (int i = 0; i < ds_NguoiChoi.size() - 1; i++) {
                    for (int j = ds_NguoiChoi.size() - 1; i < j; j--) {
                        int tt_i, tt_j;
                        tt_i = Integer.parseInt(ds_NguoiChoi.get(i).thanhtich);
                        tt_j = Integer.parseInt(ds_NguoiChoi.get(j).thanhtich);
                        if (tt_i < tt_j) {
                            NguoiChoi a = ds_NguoiChoi.get(i);
                            ds_NguoiChoi.set(i, ds_NguoiChoi.get(j));
                            ds_NguoiChoi.set(j, a);
                        }
                    }
                }
//                    adapter.notifyDataSetChanged();
                adapter = new NguoiChoiAdapter(BangXepHang.this, R.layout.item_xephang, ds_NguoiChoi);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(firebaseUser!=null){
            setProfile(firebaseUser);
        }
    }

    private void Init() {
        //init
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser=mAuth.getCurrentUser();

        //init tab
        th = findViewById(R.id.tabhost);
        th.setup();

        //B2: Tạo các tabs 123 trong tabhost

        TabHost.TabSpec tabSpec1 = th.newTabSpec("Tab 1");
        tabSpec1.setIndicator("Tài khoản");
        tabSpec1.setContent(R.id.tab1);
        TabHost.TabSpec tabSpec2 = th.newTabSpec("Tab 2");
        tabSpec2.setIndicator("Xếp hạng");
        tabSpec2.setContent(R.id.tab2);

        //B3: Thêm các tab vào th
        th.addTab(tabSpec1);
        th.addTab(tabSpec2);

        //init view
        img_avt = findViewById(R.id.iv_image);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_ruby = findViewById(R.id.tv_ruby);
        tv_thanhtich = findViewById(R.id.tv_thanhtich);
        btn_Logout = findViewById(R.id.bt_logout);
        btn_googleLogin = findViewById(R.id.btn_googleLogin);

        lv = findViewById(R.id.lv_XepHang);
        adapter = new NguoiChoiAdapter(BangXepHang.this, R.layout.item_xephang, ds_NguoiChoi);
        lv.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
//                Toast.makeText(this, ""+account.getIdToken(), Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String name = user.getDisplayName();
                                Uri image = user.getPhotoUrl();

                                //setValue ThanhTich, ruby
                                int thanhtich, ruby;
                                nguoiDung.getTT_Tien(BangXepHang.this);
                                ruby = nguoiDung.tien;
                                thanhtich = TongDiem();

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("uid", uid);
                                hashMap.put("email", email);
                                hashMap.put("ten", name);//will add later
                                hashMap.put("thanhtich", "" + thanhtich);
                                hashMap.put("ruby", "" + ruby);
                                hashMap.put("image", image + "");

                                //FirebaseData database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //path to store user data named "Users"
                                DatabaseReference reference = database.getReference("Users");
                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);
                            }

                            setProfile(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(BangXepHang.this, "Lỗi mạng...", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BangXepHang.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfile(FirebaseUser user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //path to store user data named "Users"
        DatabaseReference reference = database.getReference("Users");
        //put data within hashmap in database

        //setValue ThanhTich, ruby
        int thanhtich, ruby;
        nguoiDung.getTT_Tien(BangXepHang.this);
        ruby = nguoiDung.tien;
        thanhtich = TongDiem();

        reference.child(user.getUid()).child("thanhtich").setValue(thanhtich + "");
        reference.child(user.getUid()).child("ruby").setValue(ruby + "");

        btn_googleLogin.setVisibility(View.GONE);//Ẩn button đăng nhập

        //Hiện các thông tin
        img_avt.setVisibility(View.VISIBLE);
        tv_email.setVisibility(View.VISIBLE);
        tv_name.setVisibility(View.VISIBLE);
        tv_thanhtich.setVisibility(View.VISIBLE);
        tv_ruby.setVisibility(View.VISIBLE);
        btn_Logout.setVisibility(View.VISIBLE);

        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check untilrequired data get
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String email = "" + ds.child("email").getValue();
                    String ten = "" + ds.child("ten").getValue();
                    String thanhtich = "" + ds.child("thanhtich").getValue();
                    String ruby = "" + ds.child("ruby").getValue();
                    String image = "" + ds.child("image").getValue();

                    tv_name.setText(ten);
                    tv_email.setText(email);
                    tv_ruby.setText("Ruby: " + ruby);
                    tv_thanhtich.setText("Thành tích: " + thanhtich + " câu");

                    try {
                        Glide.with(BangXepHang.this)
                                .load(image)
                                .into(img_avt);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int TongDiem() {
        int Diem = 0;
        database = DataBase.initDatabase(BangXepHang.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM ChuDe", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int MaCD = cursor.getInt(0);

            nguoiDung.maCD = MaCD;
            nguoiDung.getTT_Cau(BangXepHang.this);
            Diem += nguoiDung.SoCau;
        }
        return Diem;
    }
}
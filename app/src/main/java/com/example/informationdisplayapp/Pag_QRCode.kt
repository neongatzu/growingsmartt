package com.example.informationdisplayapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class Pag_QRCode : AppCompatActivity() {

    var editQRcode : EditText? = null
    var btnGenerateQRcode : Button? = null
    var imgQRcode : ImageView? = null
    private lateinit var btnVoltar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pag_qrcode)

        initComponents()


        btnGenerateQRcode!!.setOnClickListener {
            if (TextUtils.isEmpty(editQRcode!!.text.toString())) {
                editQRcode!!.error = "*"
                editQRcode!!.requestFocus()
            } else {
                generateQRcode(editQRcode!!.text.toString())
            }
        }
        QRCode()

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, Pag_principal::class.java))
        }
    }


    fun initComponents() {
        editQRcode = findViewById(R.id.editQRcode)
        btnGenerateQRcode = findViewById(R.id.btnGenerateQRcode)
        imgQRcode = findViewById(R.id.imgQRcode)
        btnVoltar = findViewById(R.id.voltar)
    }

    fun generateQRcode(QRcodeContent: String) {


        var qrCode = QRCodeWriter()

        try {
            val bitMatrix = qrCode.encode(QRcodeContent, BarcodeFormat.QR_CODE, 196, 196)

            val width = bitMatrix.width
            val height = bitMatrix.height

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            imgQRcode!!.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            print(e)
        }
    }

    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun QRCode() {
        val length = 10

        val randomString = getRandomString(length)
        println(randomString)

        val mostra = findViewById<TextView>(R.id.editQRcode)

    }


}
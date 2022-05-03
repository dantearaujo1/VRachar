package com.example.vrachar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var m_contas: Int = 0
    var m_valor: Float = 0.0f
    var m_resultado: Float = 0.0f
    var m_sResultado: String = ""
    var m_tts: TextToSpeech? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main) ;
        if(savedInstanceState != null){
            println("Created");
        }
        m_tts = TextToSpeech(this,this);
    }

    override fun onDestroy(){
        if (m_tts != null){
            m_tts!!.stop();
            m_tts!!.shutdown();
        }
        super.onDestroy();
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

            val result = m_tts!!.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            } else {

            }

        } else {

        }
    }

    override fun onStart() {
        super.onStart();

        val lbResultado : TextView = findViewById(R.id.lbResultado);
        val tbValor : EditText = findViewById(R.id.tbValor);
        val tbContas : EditText = findViewById(R.id.tbContas);
        val btnShare : FloatingActionButton = findViewById(R.id.fbShare);
        val btnSpeech : FloatingActionButton = findViewById(R.id.fbSound);
        m_sResultado = getString(R.string.currencyText,"RS", m_resultado);
        lbResultado.text = m_sResultado;

        tbValor.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                m_valor = if (s.isNotEmpty())  s.toString().toFloat() else 0.00f;
                calculate();
            }
            override fun afterTextChanged(s: Editable){

                lbResultado.text = getString(R.string.currencyText,"R$".toString(), m_resultado );
                m_sResultado = lbResultado.text.toString();

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
        })
        tbContas.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                m_contas = if (s.toString().isNotEmpty())  s.toString().toInt() else 0;
                calculate();
            }
            override fun afterTextChanged(s: Editable){

                lbResultado.text = getString(R.string.currencyText,"R$", m_resultado);
                m_sResultado = lbResultado.text.toString();

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
        })
        btnShare.setOnClickListener{
            shareContent(m_sResultado.toString() + " " + getString(R.string.forEach));
        }
        btnSpeech.setOnClickListener{
            val ttsText: String = m_resultado.toString() + " reais for each person";
            speak(ttsText.toString());
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("resultado", m_sResultado);
        savedInstanceState.putFloat("valor",m_valor);
        savedInstanceState.putInt("contas",m_contas);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        m_contas = savedInstanceState.getInt("contas");
        m_sResultado = savedInstanceState.getString("resultado").toString();
        m_valor = savedInstanceState.getFloat("valor");
        super.onRestoreInstanceState(savedInstanceState)
    }

    fun shareContent(content : String){
        val intent : Intent = Intent().apply{
            action = Intent.ACTION_SEND;
            putExtra(Intent.EXTRA_TEXT, content);
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent,null);
        startActivity(shareIntent);
    }
    fun calculate(){
        m_resultado = if(m_contas > 0 && m_valor >= 0.00f) m_valor/m_contas else 0.00f;
    }
    fun speak(content : String){
        m_tts!!.speak(content, TextToSpeech.QUEUE_FLUSH,null,"");
    }

}
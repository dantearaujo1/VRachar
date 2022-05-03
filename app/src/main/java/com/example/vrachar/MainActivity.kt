package com.example.vrachar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    var m_contas: Number = 0
    var m_valor: Float = 0.0f
    var m_resultado: Float = 0.0f
    var m_sResultado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null){
            println("Created");
        }
    }

    override fun onStart() {
        super.onStart();
        m_sResultado = getString(R.string.currencyText);
        val lbResultado : TextView = findViewById(R.id.lbResultado);
        val tbValor : EditText = findViewById(R.id.tbValor);
        val tbContas : EditText = findViewById(R.id.tbContas);

        tbValor.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
            }
            override fun afterTextChanged(s: Editable){
                if(tbValor.text.isNotEmpty() && tbContas.text.isNotEmpty()){
                    m_valor = (tbValor.text.toString().toFloat());
                    if(m_contas == 0){
                        m_resultado = m_valor/1;
                    }
                    else{
                        m_resultado = m_valor/m_contas.toFloat();
                    }
                    lbResultado.text = getString(R.string.currencyText,"R$", m_resultado );
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
        })
        tbContas.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){

            }
            override fun afterTextChanged(s: Editable){
                if(tbValor.text.isNotEmpty() && tbContas.text.isNotEmpty()){
                    m_contas = tbContas.text.toString().toInt();
                    m_resultado = if(m_contas == 0){
                        m_valor/1;
                    } else{
                        m_valor/m_contas.toFloat();
                    }
                    lbResultado.text = getString(R.string.currencyText,"R$", m_resultado);
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
        })

    }

}
package com.example.calculatorapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.lang.Exception
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {


    //text
    lateinit var myText: TextView

    //Numbers
    lateinit var zero: Button
    lateinit var one: Button
    lateinit var two: Button
    lateinit var three: Button
    lateinit var four: Button
    lateinit var five: Button
    lateinit var six: Button
    lateinit var seven: Button
    lateinit var eight: Button
    lateinit var nine: Button

    //operations
    lateinit var devide: Button
    lateinit var multiply: Button
    lateinit var minus: Button
    lateinit var plus: Button
    lateinit var plusMinus: Button
    lateinit var point: Button
    lateinit var equals: Button

    lateinit var delete: Button
    lateinit var clear: Button

    lateinit var sharedPre: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPre = getSharedPreferences("WasanCalculator", MODE_PRIVATE)


        myText = findViewById(R.id.myText)

        //Numbers
        zero= findViewById(R.id.zero)
        one= findViewById(R.id.one)
        two= findViewById(R.id.two)
        three= findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)

        //operations
        devide = findViewById(R.id.devide)
        multiply = findViewById(R.id.multiply)
        minus = findViewById(R.id.minus)
        plus = findViewById(R.id.plus)
        plusMinus = findViewById(R.id.plusMinus)
        point = findViewById(R.id.point)
        equals = findViewById(R.id.equal)

        delete = findViewById(R.id.delete)
        clear = findViewById(R.id.clear)



        one.setOnClickListener{
            myText.text = "${myText.text}1"
        }
        two.setOnClickListener{
            myText.text = "${myText.text}2"
        }
        three.setOnClickListener{
            myText.text = "${myText.text}3"
        }
        four.setOnClickListener{
            myText.text = "${myText.text}4"
        }
        five.setOnClickListener{
            myText.text = "${myText.text}5"
        }
        six.setOnClickListener{
            myText.text = "${myText.text}6"
        }
        seven.setOnClickListener{
            myText.text = "${myText.text}7"
        }
        eight.setOnClickListener{
            myText.text = "${myText.text}8"
        }
        nine.setOnClickListener{
            myText.text = "${myText.text}9"
        }
        zero.setOnClickListener{
            if(myText.text.isNotEmpty())
                myText.text = "${myText.text}0"
        }


        point.setOnClickListener{
            if(myText.text.isNotEmpty())
                myText.text = "${myText.text}."
        }
        plus.setOnClickListener{
            myText.text = "${myText.text}+"
        }
        minus.setOnClickListener{
            myText.text = "${myText.text}-"
        }
        multiply.setOnClickListener{
            if(myText.text.isNotEmpty())
                myText.text = "${myText.text}*"
        }
        devide.setOnClickListener{
            if(myText.text.isNotEmpty())
                myText.text = "${myText.text}/"
        }
        clear.setOnClickListener{
            myText.text = ""
            myText.hint = "0"
        }

        delete.setOnClickListener{
            if(myText.text.isNotEmpty())
                myText.text = "${myText.text.subSequence(0, myText.text.length-1)}"
        }
        equals.setOnClickListener{
            val txt = myText.text.toString()
            val expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                myText.text = result.toString()
            }catch (e: Exception){
                myText.text = ""
                myText.hint = " Error"
            }
        }
        plusMinus.setOnClickListener{
            val txt = myText.text.toString()

            var len = txt.length
            if(len > 0){
                var lastNumber = ""
                for(i in len-1 downTo  0){
                    if(txt[i] != '*' && txt[i] != '/' ) {
                        lastNumber = txt[i] + lastNumber
                        if (txt[i] == '+' || txt[i] == '-')
                            break
                    }
                    else
                        break
                }

                if(lastNumber.length == len){
                    myText.text = "${(lastNumber.toFloat() * -1)}"
                }
                else{
                    if((lastNumber.toFloat() * -1) > 0){
                        Log.d("ressssA", lastNumber)
                        Log.d("ressssB", txt.replace(lastNumber, "") as String)

                        myText.text = txt.substring(0, len - lastNumber.length) + "+${(lastNumber.toFloat() * -1)}"
                    }else{
                        Log.d("ressssA", lastNumber)
                        Log.d("ressssB", txt.replace(lastNumber, "") as String)

                        myText.text = txt.substring(0, len - lastNumber.length)+ "${(lastNumber.toFloat() * -1)}"
                    }
                }
            }
        }

    }
    override fun onRestart() {
        super.onRestart()
        val ed: SharedPreferences.Editor = sharedPre.edit()
        ed.putString("operation", myText.text.toString())
        ed.commit()
    }

    override fun onPause() {
        super.onPause()
        val ed: SharedPreferences.Editor = sharedPre.edit()
        ed.putString("operation", myText.text.toString())
        ed.commit()
    }
}
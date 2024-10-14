package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // TextView để hiển thị kết quả
    private lateinit var textResult: TextView

    // Các biến để lưu trữ các giá trị cho phép tính
    private var valueOne = Double.NaN
    private var valueTwo: Double = 0.0

    // Biến để lưu phép tính hiện tại
    private var currentAction: Char = '0'

    // Hằng số đại diện cho các phép tính
    companion object {
        const val ADDITION = '+'
        const val SUBTRACTION = '-'
        const val MULTIPLICATION = '*'
        const val DIVISION = '/'
        const val EQU = '='
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gán TextView từ XML
        textResult = findViewById(R.id.text_result)

        // Gán các nút từ XML
        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnSub: Button = findViewById(R.id.btnSub)
        val btnMul: Button = findViewById(R.id.btnMul)
        val btnDiv: Button = findViewById(R.id.btnDiv)
        val btnEqual: Button = findViewById(R.id.btnEqual)
        val btnClear: Button = findViewById(R.id.btnC)
        val btnClearEntry: Button = findViewById(R.id.btnCE)
        val btnBackspace: Button = findViewById(R.id.btnBS)

        // Xử lý sự kiện click cho các nút số
        btn0.setOnClickListener(numberClickListener)
        btn1.setOnClickListener(numberClickListener)
        btn2.setOnClickListener(numberClickListener)
        btn3.setOnClickListener(numberClickListener)
        btn4.setOnClickListener(numberClickListener)
        btn5.setOnClickListener(numberClickListener)
        btn6.setOnClickListener(numberClickListener)
        btn7.setOnClickListener(numberClickListener)
        btn8.setOnClickListener(numberClickListener)
        btn9.setOnClickListener(numberClickListener)

        // Xử lý sự kiện click cho các nút phép toán
        btnAdd.setOnClickListener(operationClickListener)
        btnSub.setOnClickListener(operationClickListener)
        btnMul.setOnClickListener(operationClickListener)
        btnDiv.setOnClickListener(operationClickListener)

        // Xử lý sự kiện click cho nút "="
        btnEqual.setOnClickListener {
            compute() // Thực hiện phép tính
            currentAction = EQU
            textResult.text = valueOne.toString() // Hiển thị kết quả
            valueOne = Double.NaN
        }

        // Xử lý sự kiện click cho nút "C" (Xóa tất cả)
        btnClear.setOnClickListener {
            textResult.text = "0" // Đặt lại màn hình kết quả về 0
            valueOne = Double.NaN
            valueTwo = Double.NaN
        }

        // Xử lý sự kiện click cho nút "CE" (Xóa giá trị hiện tại)
        btnClearEntry.setOnClickListener {
            textResult.text = "0" // Xóa chỉ giá trị đang nhập
        }

        // Xử lý sự kiện click cho nút "BS" (Xóa ký tự cuối cùng)
        btnBackspace.setOnClickListener {
            val currentText = textResult.text.toString()
            if (currentText.isNotEmpty()) {
                // Xóa ký tự cuối cùng nếu chuỗi không rỗng
                textResult.text = currentText.dropLast(1)
                if (textResult.text.isEmpty()) {
                    textResult.text = "0"
                }
            }
        }
    }

    // Listener cho các nút số
    private val numberClickListener = View.OnClickListener { v ->
        val button = v as Button
        val currentText = textResult.text.toString()

        // Kiểm tra nếu kết quả hiện tại là "0" thì thay bằng số vừa nhập
        if (currentText == "0") {
            textResult.text = button.text
        } else {
            // Thêm số vừa nhập vào sau kết quả hiện tại
            textResult.append(button.text)
        }
    }

    // Listener cho các nút phép toán
    private val operationClickListener = View.OnClickListener { v ->
        val button = v as Button
        compute() // Thực hiện phép tính hiện tại nếu có
        currentAction = button.text.toString()[0] // Lưu lại phép toán được chọn
        textResult.text = null // Đặt lại TextView để nhập số tiếp theo
    }


    // Hàm thực hiện phép tính dựa trên phép toán và giá trị nhập vào
    private fun compute() {
        if (!valueOne.isNaN()) {
            // Lấy giá trị thứ hai từ màn hình
            valueTwo = textResult.text.toString().toDoubleOrNull() ?: return

            // Thực hiện các phép tính dựa trên `currentAction`
            when (currentAction) {
                ADDITION -> valueOne += valueTwo
                SUBTRACTION -> valueOne -= valueTwo
                MULTIPLICATION -> valueOne *= valueTwo
                DIVISION -> if (valueTwo != 0.0) {
                    valueOne /= valueTwo
                } else {
                    textResult.text = "Error" // Không thể chia cho 0
                }
            }
        } else {
            // Nếu `valueOne` chưa có giá trị, gán giá trị hiện tại cho `valueOne`
            valueOne = textResult.text.toString().toDoubleOrNull() ?: return
        }
    }

}

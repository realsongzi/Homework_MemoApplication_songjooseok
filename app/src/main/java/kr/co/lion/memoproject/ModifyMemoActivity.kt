package kr.co.lion.memoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.memoproject.databinding.ActivityModifyMemoBinding

class ModifyMemoActivity : AppCompatActivity() {

    lateinit var activityModifyMemoBinding: ActivityModifyMemoBinding

    var memo: MemoInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityModifyMemoBinding = ActivityModifyMemoBinding.inflate(layoutInflater)
        setContentView(activityModifyMemoBinding.root)

        memo = intent.getParcelableExtra<MemoInfo>("modifyMemo")

        setToolbar()
    }

    fun setToolbar(){
        activityModifyMemoBinding.apply {

            toolbarModifyMemo.apply {
                title = "메모 수정"

                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                inflateMenu(R.menu.menu_modify)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_modify_complete_memo ->{
                            memo?.apply {
                                title = activityModifyMemoBinding.textFieldModifyMemoTitle.text.toString()
                                content = activityModifyMemoBinding.textFieldModifyMemoContent.text.toString()
                            }

                            val intent = Intent()
                            intent.putExtra("modifyMemo", memo)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    }
                    true
                }


            }

            fun setTextField() {
                memo?.let { memoInfo ->
                    activityModifyMemoBinding.apply {
                        // 메모의 제목을 설정합니다.
                        textFieldModifyMemoTitle.setText(memoInfo.title)
                        textFieldModifyMemoTitle.isEnabled = true

                        // 메모의 내용을 설정합니다.
                        textFieldModifyMemoContent.setText(memoInfo.content)
                        textFieldModifyMemoContent.isEnabled = true
                    }
                }
            }
        }
    }
}
package kr.co.lion.memoproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.memoproject.databinding.ActivityShowMemoBinding

class ShowMemoActivity : AppCompatActivity() {

    lateinit var activityShowMemoBinding: ActivityShowMemoBinding

    lateinit var modifyMemoActivityLauncher: ActivityResultLauncher<Intent>

    var memo: MemoInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowMemoBinding = ActivityShowMemoBinding.inflate(layoutInflater)
        setContentView(activityShowMemoBinding.root)

        memo = intent.getParcelableExtra<MemoInfo>("memo")

        initData()
        setToolbar()
        setMemo()

    }

    fun initData(){
        // ModifyMemoActivityLauncher
        val contract3 = ActivityResultContracts.StartActivityForResult()
        modifyMemoActivityLauncher = registerForActivityResult(contract3){
            if (it.resultCode == RESULT_OK) {
                if (it.data != null){
                    memo = it.data?.getParcelableExtra("modifyMemo")
                    setModifyMemo()

                    intent.putExtra("modifiedMemo", memo)
                    setResult(RESULT_OK, intent)
                }
            }
        }
    }

    fun setToolbar(){
        activityShowMemoBinding.apply {

            toolbarShowMemo.apply {
                title = "메모"

                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.menu_show)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_modify_memo -> {
                            val modifyIntent =
                                Intent(this@ShowMemoActivity, ModifyMemoActivity::class.java)
                            modifyIntent.putExtra("modifyMemo", memo)
                            modifyMemoActivityLauncher.launch(modifyIntent)
                        }

                        R.id.menu_delete_memo -> {

                        }
                    }
                    true
                }
            }
        }
    }


    fun setMemo() {
        memo?.let { memoInfo ->
            activityShowMemoBinding.apply {
                textFieldShowMemoTitle.setText(memoInfo.title)
                textFieldShowMemoTitle.isEnabled = false

                textFieldShowMemoDate.setText(memoInfo.date)
                textFieldShowMemoDate.isEnabled = false

                textFieldShowMemoContent.setText(memoInfo.content)
                textFieldShowMemoContent.isEnabled = false
            }
        }
    }

    fun setModifyMemo(){
        memo?.let {
            activityShowMemoBinding.textFieldShowMemoTitle.setText(it.title)
            activityShowMemoBinding.textFieldShowMemoDate.setText(it.date)
            activityShowMemoBinding.textFieldShowMemoContent.setText(it.content)
            }
        }
    }


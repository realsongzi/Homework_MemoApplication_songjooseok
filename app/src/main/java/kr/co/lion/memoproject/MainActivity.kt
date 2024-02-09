package kr.co.lion.memoproject

import android.content.Intent
import android.inputmethodservice.Keyboard.Row
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.memoproject.databinding.ActivityMainBinding
import kr.co.lion.memoproject.databinding.RowMainBinding


// 1번 화면


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // InputMemoActivity Launcher
    lateinit var inputMemoActivityLauncher: ActivityResultLauncher<Intent>

    // ShowMemoActivity Launcher
    lateinit var showMemoActivityLauncher: ActivityResultLauncher<Intent>

    // ModifyMemoActivity Launcher
    lateinit var modifyMemoActivityLauncher: ActivityResultLauncher<Intent>

    var memoList =  mutableListOf<MemoInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initData()
        setToolbar()
        setView()
    }

    fun initData(){
        val contract1 = ActivityResultContracts.StartActivityForResult()
        inputMemoActivityLauncher = registerForActivityResult(contract1){
            if(it.resultCode == RESULT_OK){
                if (it.data != null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        val memoData = it.data!!.getParcelableExtra("memo", MemoInfo::class.java)
                        memoList.add(memoData!!)
                        activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                    }else{
                        val memoData = it.data!!.getParcelableExtra<MemoInfo>("memo")
                        memoList.add(memoData!!)
                        activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        val contract2 = ActivityResultContracts.StartActivityForResult()
        showMemoActivityLauncher = registerForActivityResult(contract2){
            when(it.resultCode) {
                RESULT_OK -> {
                    if (it.data != null){
                        val time = it.data?.getStringExtra("deleteMemo")
                        (time)
                    }else{
                        val time = it.data?.getStringExtra("deleteMemo")
                        (time)
                    }

                }
            }
        }
        val contract3 = ActivityResultContracts.StartActivityForResult()
        modifyMemoActivityLauncher = registerForActivityResult(contract3) {
        }
        activityMainBinding.recyclerViewMain.adapter = RecyclerViewMainAdapter()
    }


    fun setToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                title = "메모 관리"

                inflateMenu(R.menu.menu_main)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_main_input ->{
                            val inputIntent = Intent(this@MainActivity, InputMemoActivity::class.java)
                            inputMemoActivityLauncher.launch(inputIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun setView(){
        activityMainBinding.apply {
            recyclerViewMain.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)

                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }
    // RecyclerView Adapter
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        // ViewHolder
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding:RowMainBinding

            init{
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                this.rowMainBinding.root.setOnClickListener {
                    val showIntent = Intent(this@MainActivity, ShowMemoActivity::class.java)

                    val memoData = memoList[adapterPosition]
                    showIntent.putExtra("memo", memoData)

                    showMemoActivityLauncher.launch(showIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)

            val viewHolderMain = ViewHolderMain(rowMainBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowMainBinding.textViewMemoTitle.text = "${memoList[position].title}"
            holder.rowMainBinding.textViewMemoInputDate.text = memoList[position].date
        }
    }
}
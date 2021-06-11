package cn.cong.ninepictest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.cong.ninepic.NinePicItemLayout
import cn.cong.ninepic.NinePicOption
import cn.cong.ninepic.NinePicView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"

        init { // 静态构造代码块，或在Application中初始化
            NinePicView.init(
                NinePicOption.Builder()
                    .setImageLoader(NineGlideImageLoader())
                    .setCanDrag(true)
                    .build()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        npv.dataList = listOf(
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3457043476,3830081926&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2370456006,3908895016&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2893724479,3182987701&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3422671951,3725814517&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=204788913,310787938&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2263126374,543827906&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3001314946,1449082998&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=116311174,3491758342&fm=26&gp=0.jpg"
        )
//        npv.dataList = listOf("1", "2", "3", "4", "5", "6", "7", "8") // 此数据用来验证，拖动后dataList数据是否同步
        npv.setOnItemClickListener(object : NinePicView.onItemClickListener {
            override fun onNineGirdAddMoreClick(dValue: Int) {
                val s = "添加图片: 剩余可选图片${dValue}个"
                Log.e(TAG, s)
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
//                npv.addDataList()
            }

            override fun onNineGirdItemClick(pos: Int, img: String?, item: NinePicItemLayout) {
                val s = "点击第${pos + 1}图，图片内容${img}"
                Log.e(TAG, s)
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
            }

            override fun onNineGirdItemDeleted(pos: Int, img: String?, item: NinePicItemLayout) {
                val s = "删除第${pos + 1}图，图片内容${img}"
                Log.e(TAG, s)
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
                // 尽量不要延迟或劫持使用item，以防不能及时回收
            }

        })

        tv.setOnClickListener { npv.setIsEditMode(!npv.isInEditMode) }

    }
}
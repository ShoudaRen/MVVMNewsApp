package com.codinginflow.mvvmnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.codinginflow.mvvmnewsapp.databinding.ActivityMainBinding
import com.codinginflow.mvvmnewsapp.features.bookmarks.BookmarksFragment
import com.codinginflow.mvvmnewsapp.features.breakingnews.BreakingNewsFragment
import com.codinginflow.mvvmnewsapp.features.searchnews.SearchNewsFragment
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    // A:生成的绑定类将布局变量与布局中的视图关联起来。 这是为了通过这个方式来控制数据
    private lateinit var binding: ActivityMainBinding

    //    使用 lateinit 关键字，变量在定义时不需要初始化
    private lateinit var breakingNewsFragment: BreakingNewsFragment
    private lateinit var searchingNewsFragment: SearchNewsFragment
    private lateinit var bookmarksFragment: BookmarksFragment

    //建立fragments数组  用get()不直接用==防止未初始化而报错
    private val fragments: Array<Fragment>
        get() = arrayOf(
            breakingNewsFragment,
            searchingNewsFragment,
            bookmarksFragment
        )
    //模块选择
    private var selectedIndex = 0
    //选中的哪个frag
    private val selectedFragment get() = fragments[selectedIndex]

    //利用 FragmentManager 类提供的方法，您可以在运行时为 Activity 添加、移除和替换 Fragment，从而营造出动态的用户体验。
    private fun selectedFragment(selectFragment: Fragment) {
        //在 Activity 的生命周期内更改 Fragment
        var transaction = supportFragmentManager.beginTransaction();
        //index 一共就3个 正好对应
        fragments.forEachIndexed { index, fragment ->
            if (selectFragment == fragment) {
                //改变的fragment重新附上
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit();
        // 随着点击不同的导航栏的项目 更改界面小标题
        title = when (selectedFragment) {
            is BreakingNewsFragment -> getString(R.string.title_breaking_news)
            is SearchNewsFragment -> getString(R.string.title_search_news)
            is BookmarksFragment -> getString(R.string.title_bookmarks)
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //A: 绑定类的 inflate() 方法来扩充视图层次结构并将对象绑定到该层次结构 绑定的是ActivityMainXML里的数据
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//            如果之前没有fragment
        if (savedInstanceState == null) {
            breakingNewsFragment = BreakingNewsFragment()
            searchingNewsFragment = SearchNewsFragment()
            bookmarksFragment = BookmarksFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, breakingNewsFragment, TAG_BREAKING)
                .add(R.id.fragment_container, searchingNewsFragment, TAG_SEARCH)
                .add(R.id.fragment_container, bookmarksFragment, TAG_BOOK)
                .commit()
        } else {
            //调用之前的的fragment
            breakingNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_BREAKING) as BreakingNewsFragment
            searchingNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_SEARCH) as SearchNewsFragment
            bookmarksFragment =
                supportFragmentManager.findFragmentByTag(TAG_BOOK) as BookmarksFragment
            //如果为null取0
            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)
        }
        //动态体验
        selectedFragment(selectedFragment)

        //A: binding的使用,bottomNav 在mainACtiv里
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                //检测导航栏中哪个选项被触发
                R.id.breaking_icon -> breakingNewsFragment
                R.id.search_icon -> searchingNewsFragment
                R.id.bookmarks_icon -> bookmarksFragment
                else -> throw IllegalArgumentException("unexpected error")
            }
            //动态体验
            selectedFragment(fragment)
            true
        }
    }
    //为了防止在search 和bookmark framgment的后退时直接退出应用
    override fun onBackPressed() {
        if (selectedIndex != 0) {
            //直接回到breaking fragment
            binding.bottomNav.selectedItemId = R.id.breaking_icon
        } else {
            super.onBackPressed()
        }
    }
    //把新的selectedIndex储存到InstanceState （ Bundle）里
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)
    }
    companion object {
        //Optional tag name for the fragment, to later retrieve the fragment with FragmentManager.findFragmentByTag(String).
        //fragment TRag
        private const val TAG_BREAKING = "TAG_BREAKING"
        private const val TAG_SEARCH = "TAG_SEARCH"
        private const val TAG_BOOK = "TAG_BOOK"
        private const val KEY_SELECTED_INDEX = " KEY_SELECTED_INDEX"
    }

}
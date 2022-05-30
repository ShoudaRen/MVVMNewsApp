package com.codinginflow.mvvmnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.codinginflow.mvvmnewsapp.features.bookmarks.BookmarksFragment
import com.codinginflow.mvvmnewsapp.features.breakingnews.BreakingNewsFragment
import com.codinginflow.mvvmnewsapp.features.searchnews.SearchNewsFragment

class MainActivity : AppCompatActivity() {

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
    private val selecteFragment get() = fragments[selectedIndex]


    //利用 FragmentManager 类提供的方法，您可以在运行时为 Activity 添加、移除和替换 Fragment，从而营造出动态的用户体验。
    private fun selectedFragment(selectFragment: Fragment) {
        //在 Activity 的生命周期内更改 Fragment
        var transaction = supportFragmentManager.beginTransaction();
        fragments.forEachIndexed { index, fragment ->
            if (selectFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit();

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        selectedFragment(selecteFragment)

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
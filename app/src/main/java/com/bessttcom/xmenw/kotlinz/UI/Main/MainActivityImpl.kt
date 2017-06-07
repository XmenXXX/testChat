package com.bessttcom.xmenw.kotlinz.UI.Main


import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.CursorAdapter
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SimpleCursorAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.bessttcom.xmenw.kotlinz.R
import com.bessttcom.xmenw.kotlinz.UI.Main.Adapters.ViewPagerAdapter
import com.bessttcom.xmenw.kotlinz.UI.Calls.CallsFragment
import com.bessttcom.xmenw.kotlinz.UI.Chats.ChatsFragment
import com.bessttcom.xmenw.kotlinz.UI.Contacts.ContactsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.database.MatrixCursor
import android.widget.ImageView
import com.bessttcom.xmenw.kotlinz.App
import com.bessttcom.xmenw.kotlinz.UI.Messages.MessagesFragment
import com.bessttcom.xmenw.kotlinz.Utils.Auth.Auth
import com.bessttcom.xmenw.kotlinz.Utils.Database.DB
import com.squareup.otto.Subscribe


class MainActivityImpl : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainActivity {

    var mAdapter: SimpleCursorAdapter? = null
    val presenter: MainPresenter = MainPresenterImpl(this)
    var viewPagerState: Array<String>? = null
    var adapter: ViewPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        App.bus?.register(this)

        if (savedInstanceState != null)
            viewPagerState = savedInstanceState.getStringArray("viewPagerState")
        else
            viewPagerState = arrayOf("Chats", "Contacts", "Calls")

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        NSV.isFillViewport = true
        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                when (p0){
                    0 -> {
                        fab.setOnClickListener {}
                        fab.setImageResource(R.drawable.ic_chats) }
                    1 -> {
                        fab.setOnClickListener {}
                        fab.setImageResource(R.drawable.ic_contacts) }
                    2 -> {
                        fab.setOnClickListener {}
                        fab.setImageResource(R.drawable.ic_calls) }
                }
            }
        })
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_camera -> { }
            R.id.nav_gallery -> { }
            R.id.nav_slideshow -> { }
            R.id.nav_manage -> { }
            R.id.nav_share -> { }
            R.id.nav_send -> { }
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupViewPager(viewPager: ViewPager) {

        adapter = ViewPagerAdapter(supportFragmentManager)
        if (viewPagerState!!.contains("Chats")) adapter!!.addFragment(0, ChatsFragment.newInstance(), "Chats")
        else adapter!!.addFragment(0, MessagesFragment(), "Chats")
        adapter!!.addFragment(1, ContactsFragment(), "Contacts")
        adapter!!.addFragment(2, CallsFragment(), "Calls")
        viewPager.adapter = adapter
    }

    override fun onPrepareOptionsMenu(menu: Menu?): kotlin.Boolean {

        mAdapter = SimpleCursorAdapter(
                this,
                R.layout.search_item,
                null,
                arrayOf("cityName", "image"),
                intArrayOf(R.id.textView2, R.id.imageView4),
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)

        mAdapter!!.setViewBinder { view, cursor, i ->
            if (view.id == R.id.imageView4) {
                (view as ImageView).setImageResource(R.drawable.test_ic)
                return@setViewBinder true
            }
            return@setViewBinder false
        }

        val search: SearchView = menu?.findItem(R.id.search)?.actionView as SearchView
        search.suggestionsAdapter = mAdapter
        search.setIconifiedByDefault(false)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean { return false }
            override fun onQueryTextChange(p0: String): Boolean {
                if(p0.length > 1)
                    DB.contactsSearch(p0)
                return false
            }
        })
        return super.onPrepareOptionsMenu(menu)
    }

    override fun changeCursor(cursor: MatrixCursor) {
        mAdapter?.changeCursor(cursor)
    }

//    @Subscribe
    override fun viewPagerMessages(mess: BusMessages) {
        val bundle = Bundle()
        bundle.putString("id", mess.uid)
        val fragment = MessagesFragment()
        fragment.arguments = Bundle()
        adapter?.replace(0, fragment)
    }


    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putStringArray("viewPagerState", viewPagerState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
//        supportFragmentManager.beginTransaction().remove().commitAllowingStateLoss()
        super.onDestroy()
    }
}
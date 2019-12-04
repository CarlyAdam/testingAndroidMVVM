package mx.devbizne.bizne

import android.app.Application
import com.example.myapplication.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@BaseApplication)
            // modules
            modules(AppModule.myModule)
        }
    }


}
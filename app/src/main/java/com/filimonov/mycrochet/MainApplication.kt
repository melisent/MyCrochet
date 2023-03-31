package com.filimonov.mycrochet

import android.app.Application
import com.filimonov.mycrochet.di.DomainModule
import com.filimonov.mycrochet.di.ViewModelModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class MainApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(androidXModule(this@MainApplication))
        import(DomainModule)
        import(ViewModelModule)
    }
}

package com.filimonov.mycrochet

import android.app.Application
import com.filimonov.mycrochet.di.DomainModule
import com.filimonov.mycrochet.di.ViewModelModule
import org.kodein.di.DI
import org.kodein.di.DIAware

class MainApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(DomainModule)
        import(ViewModelModule)
    }
}

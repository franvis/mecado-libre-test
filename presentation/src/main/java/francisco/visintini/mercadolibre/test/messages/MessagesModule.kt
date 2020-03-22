package francisco.visintini.mercadolibre.test.messages

import dagger.Module
import dagger.Provides
import francisco.visintini.mercadolibre.test.utils.ResourceProvider

@Module
class MessagesModule {
    @Provides
    fun provideMessageManager(resourceProvider: ResourceProvider): MessageManager =
        MessageManagerImpl(resourceProvider)
}

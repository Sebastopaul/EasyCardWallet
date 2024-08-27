package com.isitechproject.easycardwallet.model.service.module

import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedBusinessCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.LoyaltyCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.SharedLoyaltyCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.SharedBusinessCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.UserServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.BusinessCardServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds abstract fun provideUserDataService(impl: UserServiceImpl): UserService
    @Binds abstract fun provideLoyaltyCardService(impl: LoyaltyCardServiceImpl): LoyaltyCardService
    @Binds abstract fun provideVisitCardService(impl: BusinessCardServiceImpl): BusinessCardService
    @Binds abstract fun provideSharedLoyaltyCardService(impl: SharedLoyaltyCardServiceImpl): SharedLoyaltyCardService
    @Binds abstract fun provideSharedVisitCardService(impl: SharedBusinessCardServiceImpl): SharedBusinessCardService
}
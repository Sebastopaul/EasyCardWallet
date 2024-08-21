package com.isitechproject.easycardwallet.model.service.module

import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.model.service.SharedCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.impl.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.impl.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds abstract fun provideUserDataService(impl: UserServiceImpl): UserService
    @Binds abstract fun provideLoyaltyCardService(impl: LoyaltyCardService): CardService
    @Binds abstract fun provideSharedLoyaltyCardService(impl: SharedLoyaltyCardService): SharedCardService
}
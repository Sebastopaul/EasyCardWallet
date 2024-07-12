package com.isitechproject.easycardwallet.model.service.module

import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.GroupMemberService
import com.isitechproject.easycardwallet.model.service.GroupService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.GroupMemberServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.GroupServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.LoyaltyCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.SharedLoyaltyCardServiceImpl
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
    @Binds abstract fun provideGroupService(impl: GroupServiceImpl): GroupService
    @Binds abstract fun provideGroupMemberService(impl: GroupMemberServiceImpl): GroupMemberService
    @Binds abstract fun provideLoyaltyCardService(impl: LoyaltyCardServiceImpl): LoyaltyCardService
    @Binds abstract fun provideSharedLoyaltyCardService(impl: SharedLoyaltyCardServiceImpl): SharedLoyaltyCardService
}
package com.isitechproject.easycardwallet.model.service.impl.exception

class NotAuthenticatedException(
    override val message: String = "You must be authenticated to make this action."
) : Exception(message)
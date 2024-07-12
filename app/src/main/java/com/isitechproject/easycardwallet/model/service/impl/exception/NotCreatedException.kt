package com.isitechproject.easycardwallet.model.service.impl.exception

class NotCreatedException(
    override val message: String = "Could not create resource."
) : Exception(message)
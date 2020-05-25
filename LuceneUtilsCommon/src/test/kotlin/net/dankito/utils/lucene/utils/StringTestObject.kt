package net.dankito.utils.lucene.utils

import net.dankito.utils.lucene.mapper.IdentifiableByString


class StringTestObject(override val id: String) : IdentifiableByString {

    internal constructor() : this("") // for object deserializers


    override fun toString(): String {
        return id
    }

}
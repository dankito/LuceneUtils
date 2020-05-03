package net.dankito.utils.lucene.utils


class StringTestObject(val name: String) {

    internal constructor() : this("") // for object deserializers


    override fun toString(): String {
        return name
    }

}
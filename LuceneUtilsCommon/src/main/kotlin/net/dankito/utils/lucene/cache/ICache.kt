package net.dankito.utils.lucene.cache

import net.dankito.utils.lucene.mapper.Identifiable


interface ICache<ID> {

    fun add(item: Identifiable<ID>)

    fun get(id: ID): Identifiable<ID>?

    fun remove(item: Identifiable<ID>)

}
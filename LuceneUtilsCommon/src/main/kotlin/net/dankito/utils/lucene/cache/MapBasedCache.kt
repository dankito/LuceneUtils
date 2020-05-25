package net.dankito.utils.lucene.cache

import net.dankito.utils.lucene.mapper.Identifiable
import java.util.concurrent.ConcurrentHashMap


open class MapBasedCache<ID> : ICache<ID> {

    protected val items = ConcurrentHashMap<ID, Identifiable<ID>>()


    override fun add(item: Identifiable<ID>) {
        items.put(item.id, item)
    }

    override fun get(id: ID): Identifiable<ID>? {
        return items[id]
    }

    override fun remove(item: Identifiable<ID>) {
        items.remove(item.id)
    }

}
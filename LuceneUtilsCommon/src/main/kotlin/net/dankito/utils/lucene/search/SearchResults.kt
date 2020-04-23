package net.dankito.utils.lucene.search


open class SearchResults @JvmOverloads constructor(
		val totalHits: Long,
		val hits: List<SearchResult>,
		val error: Exception? = null
) {

	constructor(error: Exception) : this(-1, listOf(), error)


	open val countRetrievedHits: Int = hits.size


	override fun toString(): String {
		return "$countRetrievedHits (of total $totalHits) hits"
	}

}
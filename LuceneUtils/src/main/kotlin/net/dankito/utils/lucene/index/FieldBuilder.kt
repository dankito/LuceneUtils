package net.dankito.utils.lucene.index

import net.dankito.utils.lucene.extensions.storedValue
import org.apache.lucene.document.*
import java.time.Instant


open class FieldBuilder : FieldBuilderBase() {

	override fun createIntField(name: String, value: Int): IntPoint {
		return IntPoint(name, value)
	}

	override fun createLongField(name: String, value: Long): LongPoint {
		return LongPoint(name, value)
	}

	override fun createFloatField(name: String, value: Float): FloatPoint {
		return FloatPoint(name, value)
	}

	override fun createDoubleField(name: String, value: Double): DoublePoint {
		return DoublePoint(name, value)
	}


	open fun instantField(name: String, value: Instant): Field {
		return longField(name, value.storedValue())
	}

	@JvmOverloads
	open fun instantField(name: String, value: Instant, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(instantField(name, value)).apply {
			if (store) {
				add(storedField(name, value))
			}
			if (sortable) {
				add(sortField(name, value))
			}
		}
	}

	open fun nullableInstantField(name: String, value: Instant?): Field? {
		return if (value == null) null else instantField(name, value)
	}

	open fun instantField(name: String, values: List<Instant>): List<Field> {
		return values.map { value ->
			instantField(name, value)
		}
	}

	open fun nullableInstantField(name: String, values: List<Instant>?): List<Field>? {
		return values?.map { value ->
			instantField(name, value)
		}
	}


	open fun storedField(name: String, value: Instant): StoredField {
		return storedField(name, value.storedValue())
	}


	override fun sortField(name: String, value: Long): Field {
		return SortedNumericDocValuesField(name, value)
	}

	open fun sortField(name: String, value: Instant): Field {
		return sortField(name, value.storedValue())
	}

}
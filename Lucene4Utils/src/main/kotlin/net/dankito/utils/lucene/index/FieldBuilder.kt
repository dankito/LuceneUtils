package net.dankito.utils.lucene.index

import org.apache.lucene.document.*


open class FieldBuilder : FieldBuilderBase<IntField, LongField, FloatField, DoubleField>() {

	override fun createIntField(name: String, value: Int): IntField {
		return IntField(name, value, Field.Store.NO)
	}

	override fun createLongField(name: String, value: Long): LongField {
		return LongField(name, value, Field.Store.NO)
	}

	override fun createFloatField(name: String, value: Float): FloatField {
		return FloatField(name, value, Field.Store.NO)
	}

	override fun createDoubleField(name: String, value: Double): DoubleField {
		return DoubleField(name, value, Field.Store.NO)
	}


	// TODO

//	open fun sortField(name: String, value: Int): SortedNumericDocValuesField {
//		return sortField(name, value.toLong())
//	}
//
//	open fun sortField(name: String, value: Long): SortedNumericDocValuesField {
//		return SortedNumericDocValuesField(name, value)
//	}
//
//	open fun sortField(name: String, value: Float): SortedNumericDocValuesField {
//		return sortField(name, NumericUtils.floatToSortableInt(value))
//	}
//
//	open fun sortField(name: String, value: Double): SortedNumericDocValuesField {
//		return sortField(name, NumericUtils.doubleToSortableLong(value))
//	}

}
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


	override fun sortField(name: String, value: Long): Field {
		return NumericDocValuesField(name, value)
	}

}
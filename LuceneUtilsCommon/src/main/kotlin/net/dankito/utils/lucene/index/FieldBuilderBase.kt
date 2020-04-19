package net.dankito.utils.lucene.index

import org.apache.lucene.document.*
import org.apache.lucene.util.BytesRef
import java.util.*


abstract class FieldBuilderBase<IntPoint, LongPoint, FloatPoint, DoublePoint> {

	protected abstract fun createIntField(name: String, value: Int): IntPoint

	protected abstract fun createLongField(name: String, value: Long): LongPoint

	protected abstract fun createFloatField(name: String, value: Float): FloatPoint

	protected abstract fun createDoubleField(name: String, value: Double): DoublePoint


	@JvmOverloads
	open fun fullTextSearchField(name: String, value: String, store: Boolean = false): TextField {
		return textField(name, value, store)
	}

	@JvmOverloads
	open fun nullableFullTextSearchField(name: String, value: String?, store: Boolean = false): TextField? {
		return nullableTextField(name, value, store)
	}

	@JvmOverloads
	open fun fullTextSearchField(name: String, values: List<String>, store: Boolean = false): List<TextField> {
		return textField(name, values, store)
	}

	@JvmOverloads
	open fun nullableFullTextSearchField(name: String, values: List<String>?, store: Boolean = false): List<TextField>? {
		return nullableTextField(name, values, store)
	}

	@JvmOverloads
	open fun textField(name: String, value: String, store: Boolean = false): TextField {
		return if (store) {
			storedTextField(name, value)
		}
		else {
			unstoredTextField(name, value)
		}
	}

	open fun storedTextField(name: String, value: String): TextField {
		return TextField(name, value, Field.Store.YES)
	}

	open fun unstoredTextField(name: String, value: String): TextField {
		return TextField(name, value, Field.Store.NO)
	}

	@JvmOverloads
	open fun nullableTextField(name: String, value: String?, store: Boolean = false): TextField? {
		return if (value == null) null else textField(name, value, store)
	}

	@JvmOverloads
	open fun textField(name: String, values: List<String>, store: Boolean = false): List<TextField> {
		return values.map { value ->
			textField(name, value, store)
		}
	}

	@JvmOverloads
	open fun nullableTextField(name: String, values: List<String>?, store: Boolean = false): List<TextField>? {
		return values?.map { value ->
			textField(name, value, store)
		}
	}


	@JvmOverloads
	open fun keywordField(name: String, value: String, store: Boolean = true): StringField {
		return stringField(name, value, store)
	}

	@JvmOverloads
	open fun nullableKeywordField(name: String, value: String?, store: Boolean = true): StringField? {
		return if (value == null) null else keywordField(name, value, store)
	}

	@JvmOverloads
	open fun keywordField(name: String, values: List<String>, store: Boolean = true): List<StringField> {
		return values.map { value ->
			keywordField(name, value, store)
		}
	}

	@JvmOverloads
	open fun stringField(name: String, value: String, store: Boolean = true): StringField {
		return if (store) {
			storedStringField(name, value)
		}
		else {
			unstoredStringField(name, value)
		}
	}

	open fun storedStringField(name: String, value: String): StringField {
		return StringField(name, value, Field.Store.YES)
	}

	open fun unstoredStringField(name: String, value: String): StringField {
		return StringField(name, value, Field.Store.NO)
	}

	@JvmOverloads
	open fun nullableStringField(name: String, value: String?, store: Boolean = true): StringField? {
		return if (value == null) null else stringField(name, value, store)
	}

	@JvmOverloads
	open fun stringField(name: String, values: List<String>, store: Boolean = true): List<StringField> {
		return values.map { value ->
			stringField(name, value, store)
		}
	}

	@JvmOverloads
	open fun nullableStringField(name: String, values: List<String>?, store: Boolean = true): List<StringField>? {
		return values?.map { value ->
			stringField(name, value, store)
		}
	}


	open fun intField(name: String, value: Int): IntPoint {
		return createIntField(name, value)
	}

	open fun nullableIntField(name: String, value: Int?): IntPoint? {
		return if (value == null) null else intField(name, value)
	}

	open fun intField(name: String, values: List<Int>): List<IntPoint> {
		return values.map { value ->
			intField(name, value)
		}
	}

	open fun nullableIntField(name: String, values: List<Int>?): List<IntPoint>? {
		return values?.map { value ->
			intField(name, value)
		}
	}


	open fun longField(name: String, value: Long): LongPoint {
		return createLongField(name, value)
	}

	open fun nullableLongField(name: String, value: Long?): LongPoint? {
		return if (value == null) null else longField(name, value)
	}

	open fun longField(name: String, values: List<Long>): List<LongPoint> {
		return values.map { value ->
			longField(name, value)
		}
	}

	open fun nullableLongField(name: String, values: List<Long>?): List<LongPoint>? {
		return values?.map { value ->
			longField(name, value)
		}
	}


	open fun floatField(name: String, value: Float): FloatPoint {
		return createFloatField(name, value)
	}

	open fun nullableFloatField(name: String, value: Float?): FloatPoint? {
		return if (value == null) null else floatField(name, value)
	}

	open fun floatField(name: String, values: List<Float>): List<FloatPoint> {
		return values.map { value ->
			floatField(name, value)
		}
	}

	open fun nullableFloatField(name: String, values: List<Float>?): List<FloatPoint>? {
		return values?.map { value ->
			floatField(name, value)
		}
	}


	open fun doubleField(name: String, value: Double): DoublePoint {
		return createDoubleField(name, value)
	}

	open fun nullableDoubleField(name: String, value: Double?): DoublePoint? {
		return if (value == null) null else doubleField(name, value)
	}

	open fun doubleField(name: String, values: List<Double>): List<DoublePoint> {
		return values.map { value ->
			doubleField(name, value)
		}
	}

	open fun nullableDoubleField(name: String, values: List<Double>?): List<DoublePoint>? {
		return values?.map { value ->
			doubleField(name, value)
		}
	}


	open fun dateField(name: String, value: Date): LongPoint {
		return longField(name, value.time)
	}

	open fun nullableDateField(name: String, value: Date?): LongPoint? {
		return if (value == null) null else dateField(name, value)
	}

	open fun dateField(name: String, values: List<Date>): List<LongPoint> {
		return values.map { value ->
			dateField(name, value)
		}
	}

	open fun nullableDateField(name: String, values: List<Date>?): List<LongPoint>? {
		return values?.map { value ->
			dateField(name, value)
		}
	}


	open fun storedField(name: String, value: String): StoredField {
		return StoredField(name, value)
	}

	open fun nullableStoredField(name: String, value: String?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}

	open fun storedField(name: String, value: ByteArray): StoredField {
		return StoredField(name, value)
	}

	open fun nullableStoredField(name: String, value: ByteArray?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}


	open fun storedField(name: String, value: Int): StoredField {
		return StoredField(name, value)
	}

	open fun nullableStoredField(name: String, value: Int?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}

	open fun storedIntListField(name: String, values: List<Int>): List<StoredField> {
		return values.map { value ->
			storedField(name, value)
		}
	}

	open fun nullableStoredIntListField(name: String, values: List<Int>?): List<StoredField>? {
		return values?.map { value ->
			storedField(name, value)
		}
	}


	open fun storedField(name: String, value: Long): StoredField {
		return StoredField(name, value)
	}

	open fun nullableStoredField(name: String, value: Long?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}

	open fun storedLongListField(name: String, values: List<Long>): List<StoredField> {
		return values.map { value ->
			storedField(name, value)
		}
	}

	open fun nullableStoredLongListField(name: String, values: List<Int>?): List<StoredField>? {
		return values?.map { value ->
			storedField(name, value)
		}
	}


	open fun storedField(name: String, value: Float): StoredField {
		return StoredField(name, value)
	}

	open fun nullableStoredField(name: String, value: Float?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}

	open fun storedFloatListField(name: String, values: List<Float>): List<StoredField> {
		return values.map { value ->
			storedField(name, value)
		}
	}

	open fun nullableStoredFloatListField(name: String, values: List<Float>?): List<StoredField>? {
		return values?.map { value ->
			storedField(name, value)
		}
	}


	open fun storedField(name: String, value: Double): StoredField {
		return StoredField(name, value)
	}

	open fun nullableStoredField(name: String, value: Double?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}

	open fun storedDoubleListField(name: String, values: List<Double>): List<StoredField> {
		return values.map { value ->
			storedField(name, value)
		}
	}

	open fun nullableStoredDoubleListField(name: String, values: List<Double>?): List<StoredField>? {
		return values?.map { value ->
			storedField(name, value)
		}
	}


	open fun storedField(name: String, value: Date): StoredField {
		return storedField(name, value.time)
	}

	open fun nullableStoredField(name: String, value: Date?): StoredField? {
		return if (value == null) null else storedField(name, value)
	}

	open fun storedDateListField(name: String, values: List<Date>): List<StoredField> {
		return values.map { value ->
			storedField(name, value)
		}
	}

	open fun nullableStoredDateListField(name: String, values: List<Date>?): List<StoredField>? {
		return values?.map { value ->
			storedField(name, value)
		}
	}


	@JvmOverloads
	open fun sortField(name: String, value: String, caseInsensitive: Boolean = true): SortedDocValuesField {
		val adjustedValue = if (caseInsensitive) value.toLowerCase() else value

		return SortedDocValuesField(name, BytesRef(adjustedValue))
	}

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
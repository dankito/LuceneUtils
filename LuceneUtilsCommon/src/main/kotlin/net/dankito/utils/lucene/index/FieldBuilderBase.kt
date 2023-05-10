package net.dankito.utils.lucene.index

import net.dankito.utils.lucene.Constants.Companion.DefaultBigDecimalPrecision
import org.apache.lucene.document.*
import org.apache.lucene.util.BytesRef
import org.apache.lucene.util.NumericUtils
import java.math.BigDecimal
import java.util.*


abstract class FieldBuilderBase {

	protected abstract fun createIntField(name: String, value: Int): Field

	protected abstract fun createLongField(name: String, value: Long): Field

	protected abstract fun createFloatField(name: String, value: Float): Field

	protected abstract fun createDoubleField(name: String, value: Double): Field


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
	open fun keywordField(name: String, value: String, store: Boolean = true, sortable: Boolean = false): List<Field> {
		return mutableListOf<Field>(stringField(name, value, store)).apply {
			if (sortable) {
				add(sortField(name, value))
			}
		}
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


	open fun intField(name: String, value: Int): Field {
		return createIntField(name, value)
	}

	@JvmOverloads
	open fun intField(name: String, value: Int, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(intField(name, value)).apply {
			if (store) {
				add(storedField(name, value))
			}
			if (sortable) {
				add(sortField(name, value))
			}
		}
	}

	open fun nullableIntField(name: String, value: Int?): Field? {
		return if (value == null) null else intField(name, value)
	}

	open fun intField(name: String, values: List<Int>): List<Field> {
		return values.map { value ->
			intField(name, value)
		}
	}

	open fun nullableIntField(name: String, values: List<Int>?): List<Field>? {
		return values?.map { value ->
			intField(name, value)
		}
	}


	open fun longField(name: String, value: Long): Field {
		return createLongField(name, value)
	}

	@JvmOverloads
	open fun longField(name: String, value: Long, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(longField(name, value)).apply {
			if (store) {
				add(storedField(name, value))
			}
			if (sortable) {
				add(sortField(name, value))
			}
		}
	}

	open fun nullableLongField(name: String, value: Long?): Field? {
		return if (value == null) null else longField(name, value)
	}

	open fun longField(name: String, values: List<Long>): List<Field> {
		return values.map { value ->
			longField(name, value)
		}
	}

	open fun nullableLongField(name: String, values: List<Long>?): List<Field>? {
		return values?.map { value ->
			longField(name, value)
		}
	}


	open fun floatField(name: String, value: Float): Field {
		return createFloatField(name, value)
	}

	@JvmOverloads
	open fun floatField(name: String, value: Float, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(floatField(name, value)).apply {
			if (store) {
				add(storedField(name, value))
			}
			if (sortable) {
				add(sortField(name, value))
			}
		}
	}

	open fun nullableFloatField(name: String, value: Float?): Field? {
		return if (value == null) null else floatField(name, value)
	}

	open fun floatField(name: String, values: List<Float>): List<Field> {
		return values.map { value ->
			floatField(name, value)
		}
	}

	open fun nullableFloatField(name: String, values: List<Float>?): List<Field>? {
		return values?.map { value ->
			floatField(name, value)
		}
	}


	open fun doubleField(name: String, value: Double): Field {
		return createDoubleField(name, value)
	}

	@JvmOverloads
	open fun doubleField(name: String, value: Double, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(doubleField(name, value)).apply {
			if (store) {
				add(storedField(name, value))
			}
			if (sortable) {
				add(sortField(name, value))
			}
		}
	}

	open fun nullableDoubleField(name: String, value: Double?): Field? {
		return if (value == null) null else doubleField(name, value)
	}

	open fun doubleField(name: String, values: List<Double>): List<Field> {
		return values.map { value ->
			doubleField(name, value)
		}
	}

	open fun nullableDoubleField(name: String, values: List<Double>?): List<Field>? {
		return values?.map { value ->
			doubleField(name, value)
		}
	}


	open fun dateField(name: String, value: Date): Field {
		return longField(name, value.time)
	}

	@JvmOverloads
	open fun dateField(name: String, value: Date, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(dateField(name, value)).apply {
			if (store) {
				add(storedField(name, value))
			}
			if (sortable) {
				add(sortField(name, value))
			}
		}
	}

	open fun nullableDateField(name: String, value: Date?): Field? {
		return if (value == null) null else dateField(name, value)
	}

	open fun dateField(name: String, values: List<Date>): List<Field> {
		return values.map { value ->
			dateField(name, value)
		}
	}

	open fun nullableDateField(name: String, values: List<Date>?): List<Field>? {
		return values?.map { value ->
			dateField(name, value)
		}
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimalField(name: String, value: BigDecimal, precision: Int = DefaultBigDecimalPrecision): Field {
		return longField(name, storedValue(value, precision))
	}

	@JvmOverloads
	open fun bigDecimalField(name: String, value: BigDecimal, precision: Int = DefaultBigDecimalPrecision, store: Boolean = false, sortable: Boolean = false): List<Field> {
		return mutableListOf(bigDecimalField(name, value, precision)).apply {
			if (store) {
				add(storedField(name, value, precision))
			}
			if (sortable) {
				add(sortField(name, value, precision))
			}
		}
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableBigDecimalField(name: String, value: BigDecimal?, precision: Int = DefaultBigDecimalPrecision): Field? {
		return if (value == null) null else bigDecimalField(name, value, precision)
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimalField(name: String, values: List<BigDecimal>, precision: Int = DefaultBigDecimalPrecision): List<Field> {
		return values.map { value ->
			bigDecimalField(name, value, precision)
		}
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableBigDecimalField(name: String, values: List<BigDecimal>?, precision: Int = DefaultBigDecimalPrecision): List<Field>? {
		return values?.map { value ->
			bigDecimalField(name, value, precision)
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


	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun storedField(name: String, value: BigDecimal, precision: Int = DefaultBigDecimalPrecision): StoredField {
		return storedField(name, storedValue(value, precision))
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableStoredField(name: String, value: BigDecimal?, precision: Int = DefaultBigDecimalPrecision): StoredField? {
		return if (value == null) null else storedField(name, value, precision)
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun storedBigDecimalListField(name: String, values: List<BigDecimal>, precision: Int = DefaultBigDecimalPrecision): List<StoredField> {
		return values.map { value ->
			storedField(name, value, precision)
		}
	}

	/**
	 * BigDecimals are converted to Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal should be stored has to be specified.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableStoredBigDecimalListField(name: String, values: List<BigDecimal>?, precision: Int = DefaultBigDecimalPrecision): List<StoredField>? {
		return values?.map { value ->
			storedField(name, value, precision)
		}
	}

	protected open fun storedValue(value: BigDecimal, precision: Int): Long {
		return value.scaleByPowerOfTen(precision).toLong()
	}


	@JvmOverloads
	open fun sortField(name: String, value: String, ignoreCase: Boolean = true): SortedDocValuesField {
		val adjustedValue = if (ignoreCase) value.toLowerCase() else value

		return SortedDocValuesField(name, BytesRef(adjustedValue))
	}


	/*		Sorting		*/

	abstract fun sortField(name: String, value: Long): Field

	open fun sortField(name: String, value: Int): Field {
		return sortField(name, value.toLong())
	}

	open fun sortField(name: String, value: Float): Field {
		return sortField(name, NumericUtils.floatToSortableInt(value))
	}

	open fun sortField(name: String, value: Double): Field {
		return sortField(name, NumericUtils.doubleToSortableLong(value))
	}

	open fun sortField(name: String, value: Date): Field {
		return sortField(name, value.time)
	}

	open fun sortField(name: String, value: BigDecimal, precision: Int = DefaultBigDecimalPrecision): Field {
		return sortField(name, storedValue(value, precision))
	}

}
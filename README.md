# rufous-android
Utility libraries for android developers

The most recent version is [ ![Download](https://api.bintray.com/packages/bpappin/pappin/rufous-android/images/download.svg) ](https://bintray.com/bpappin/pappin/rufous-android/_latestVersion).


# Rufous Utilities #

### Installation ###
```gradle
	dependencies {
		implementation 'pappin.rufous:rufous-utilities:1.0.0'
	}
```

### Components ###
 * `StringUtil`: Various string testing methods that are not attached to the Android API..
 * `MoneyUtil`: formatting and conversion from bankers value to BigDecimal.
 * `Popularity`: Basic time based popularity algorithm.

# Rufous Widgets #

### Installation ###
```gradle
	dependencies {
		implementation 'pappin.rufous:rufous-widgets:1.0.0'
	}
```

### Components ###
 * `CursorRecyclerViewAdapter`: Providers a cursor adapter for the RecyclerView widget.
 * `EmptyRecyclerView`: A RecyclerView that can have a TextView attached to it, that shows a message if the RecyclerView has no rows.
 * `DisablingRadioGroup`: A RadioGroup that can disable all its radio buttons at once.
 * `DrawableTextView`: A TextView that properly tints its drawables witht he same colour as the text.
 * `LetterTileProvider`: A tool for creating placeholder avatar images using a string (name).
 
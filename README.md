# Mercado Libre Test

Test project that helps you to search and visualize Mercado Libre products.

## This project uses:
- [Detekt](https://github.com/arturbosch/detekt) and [Spotless](https://github.com/diffplug/spotless)
- [Timber](https://github.com/JakeWharton/timber)
- [Moshi](https://github.com/square/moshi) and [Retrofit2](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [Groupie](https://github.com/lisawray/groupie)
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [KotlinX Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Dagger](https://github.com/google/dagger)
- [Hyperion](https://github.com/willowtreeapps/Hyperion-Android)
- [FacebookShimmer](https://github.com/facebook/shimmer-android)
- [CanaryLeak](https://github.com/square/leakcanary)
- [Mockk](https://github.com/mockk/mockk)
- [Jacoco](https://github.com/jacoco/jacoco)

## Extras:
This project provides a pre commit hook that runs both detekt and spotlessCheck to
avoid code smells, format the code and run ktlint for code styling

To enable it run in the root of your project:
`git config core.hooksPath .githooks`

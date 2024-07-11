Hilt: Imp points to notice:
> All apps that use Hilt must contain an Application class that is annotated with @HiltAndroidApp. @HiltAndroidApp triggers Hilt's code generation,
	including a base class for your application that serves as the application-level dependency container.

> Hilt can provide dependencies to other Android classes that have the @AndroidEntryPoint annotation: Application (by using @HiltAndroidApp)
	,ViewModel (by using @HiltViewModel),Activity, Fragment,Service,View,BroadCastReciver
	note:If you annotate an Android class with @AndroidEntryPoint, then you also must annotate Android classes that depend on it.

> To obtain dependencies from a component, use the @Inject annotation to perform field injection:
		Note: Fields injected by Hilt cannot be private. Attempting to inject a private field with Hilt results in a compilation error.
> Defining hilt binding: To perform field injection, Hilt needs to know how to provide instances of the necessary dependencies from the corresponding component.
		One way to provide binding information to Hilt is constructor injection. Use the @Inject annotation on the constructor of a class to tell Hilt how to provide
		instances of that class.
> A Hilt module::Sometimes a type cannot be constructor-injected. This can happen for multiple reasons. For example, you cannot constructor-inject an interface.
	A hilt module is a class that is annotated with @Module. Like a Dagger module, it informs Hilt how to provide instances of certain types. Unlike Dagger
	modules, you must annotate Hilt modules with @InstallIn to tell Hilt which Android class each module will be used or installed in.

> Inject instances with @Provides: Constructor injection is also not possible if you don't own the class because it comes from an external library (classes like
	Retrofit, OkHttpClient, or Room databases), or if instances must be created with the builder pattern.
> Predefined qualifiers in Hilt: we might need the Context class from either the application or the activity, Hilt provides the @ApplicationContext and
	@ActivityContext qualifiers

Fo more insights : https://developer.android.com/training/dependency-injection/hilt-android#kotlin
# ExampleA

This is a very simple spring boot v1.3.8 java app that creatings a http session (JSESSION cookie) upon first access.  This app can be used to verify session stickyness.

The http response defaults to "Hello World", however you can override this message by setting the following env variable:


`cf set-env examplea application.message "My message"`

The output also prints out the application URIs as found in the app's environment.  Specifically the value of `vcap.application.application_uris`.



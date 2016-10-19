# HttpSessionExampleApp 

This is a very simple spring boot v1.3.8 java app that can be used for testing stateless and stateful web applications.  It allows the creation of a http session which creates a JSESSIONID cookie.  This app can be used to verify session stickiness.

The app allows for a custom message override. It defaults to "Hello World", you can override this message by setting the following env variable:

`cf set-env examplea application.message "ProdA"`

This application has been used to test external global load balancers (e.g., BigIP F5) to ensure active/active datacenter stickiness.   

Screenshot:

![Screenshot](docs/screenshot.png?raw=true)

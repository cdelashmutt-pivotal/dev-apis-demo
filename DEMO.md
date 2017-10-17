# Demo

## Prepare
1. Run through the ["Build and Deploy"](README.md#building-and-deploying) steps to create all the necessary apps, services, and broker registrations.
This is important because the Circuit Breaker dashboard can take a long time to initialize.
2. Unbind the `epay` service instance from the `bank-client-app` application, and restart it.  Delete the `epay` service instance.  This will give you a chance to show how binding a service adds the "eBill" functionality to the application.

## Demo
This demo is meant to allow you to stay as high, or dive as deep as you want to.  It follows a paradigm of showing "what" first in 5 minutes, and then diving into "how" if there is interest.

### The "What" - Better Developer Experience
1. With all apps deployed, start off by pointing out how creating an API is not always easy, but getting it to API 
consumers in a form they can easily use can be even harder.  To make an easy to consume API, you need to provide 
documentation about the APIs, you need to let developers where your API is and how to connect.  If you meter and secure 
your APIs, you need to provide feedback to API consumers about consumption and manage credentials.  Pivotal provides 
solutions that can help you deal with all these challenges more easily to allow you more time to perfect your APIs.
2. Navigate to the Apps Manager UI in the browser.  Go to the space you have deployed the `bank-client-app`.
3. Right click the hostname for the `bank-client-app` you have deployed and open it in a new tab.  On the resulting page, click the "Login" button to go to the bank dashboard page.
4. Point out that there are no eBills showing for this client, but we're going to get access to some from a third party service.  Logout of the app.  Close the tab for the application.
5. Go back to the tab for the Apps Manager, and go into the details for the `bank-client-app` by clicking its name.
6. Click on the "Service" tab.
7. Click the "New Service" tab.
8. In the search box of the resulting dialog, type "Fi" and select the "Fiserv APIs" service.
9. Click the "Show Details" link to expand the details about the service that can be communicated to developers.
10. Check the radio button for the "epay" service plan, and click the "Select Plan" button.
11. Enter "epay" as the instance name, and click "Create" to create and bind the service.
12. Click on the "epay" service instance that we just created an bound to the app.
13. From this page, you can talk about how the service abstraction in Cloud Foundry allows a service provider to communicate information to a consumer of your API via data and links it can pass back to Cloud Foundry.  Click on the "Docs" link and show how the developer can access API documentation for your service.
14. Back in the Apps Manager tab, click on the "Support" link to show how a developer could link to an online support channel for your service.
15. Back in the Apps Manager tab, click on the "Manage" link to show how you can connect developers to a management console that a service provider can provide that is specific to the service instance they created.
16. Back in the Apps Manager tab, click the restart button for the `bank-client-app` application.  At this point you could go to the logs to monitor the startup of the app.
17. Once the app has started, click the "View App" link in the top right corner of the page to open a new tab on the `bank-client-app`.
18. In the resulting tab, click the "Login" button.
19. In the resulting "Dashboard" screen, show the green box near the top of the page that should show a count of eBills greater than 0.  Click on the "View Details" text of the box to navigate to the list of eBills.
20. Navigate around the list to show that we got some data from the bound service.

### The "How" - Pivotal CF, and Spring
1. (In progress)
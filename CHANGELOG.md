# Changelog

## Versioning Rules

Project uses [Semantic Versioning](http://semver.org/).

Given version x.y.z

* x - Major version. Backward compatibility is broken. Refactoring or big features.
* y - Minor version. Backward compatibile with previous version. New features.
* z - Patch version. Backward compatibile with previous version. Bug fix or small features. 

**In case of x == 0 backward compatibility is not guaranteed.**

### [0.0.11]() (29-08-2017)

* Add callback telling that SpringQueueConsumer and SpringQueueConsumer are initialized
* Fixed passing null property file to QueueConfigsReader

### [0.0.10]() (27-08-2017)

* Added ThreadLifecycleListener#executed to measure overall queue performance.
* Specifying ThreadLifecycleListener per queue

### [0.0.9]() (26-08-2017)

* Fixed ability to disable queue processing via thread count
* Renamed ThreadLifecycleListener#crashedOnPickedTask to ThreadLifecycleListener#crashed
* Constructors of builder classes are made private
* Return value of QueueProducer#enqueue changed to primitive
* Added reenqueue by actor via QueueActorDao#reenqueue

### [0.0.8]() (24-08-2017)

Renamed classes:
* Queue -> QueueConsumer
* Enqueuer -> QueueProducer
* ShardRouter -> QueueShardRouter
* QueueThreadLifecycleListener -> ThreadLifecycleListener
* QueueAction -> TaskExecutionResult
* PayloadTransformer -> TaskPayloadTransformer

### [0.0.7]() (24-08-2017)

* Upgrade Spring to 4.3.7

### [0.0.6]() (24-08-2017)

* Same queue names in different tables are forbidden
* Reading configuration from file (QueueConfigsReader)

### [0.0.5]() (20-08-2017)

* Translated README to English

### [0.0.1]() (08-08-2017)

* Initial version
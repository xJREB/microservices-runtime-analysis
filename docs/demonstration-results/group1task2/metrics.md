# Number of Services Involved in the Compound Service (NSIC)

## Description
Number of services involved in the compound service

## Interpretation
Value range: [0,14], lower is better

## Results

- mine: 8
- fanout: 8
- entry-cache: 1
- index: 1
- follow: 7
- timeline0: 7
- home: 8
- timeline1: 7
- search: 9
- post: 10
- reserve: 1
- api: 11
- timeline-shard: 1
- entry-store: 7


# Services Interdependence in the System (SIY)

## Description
Count of pairs of services which depend on each other

## Interpretation
Value range: [0,91], lower is better

## Result
2


# Absolute Importance of the Service (AIS)

## Description
Number of services which depend on a service

## Interpretation
Value range: [0,13], lower is better

## Results

- mine: 0
- fanout: 1
- entry-cache: 3
- index: 2
- follow: 5
- timeline0: 4
- home: 0
- timeline1: 4
- search: 0
- post: 1
- reserve: 3
- api: 0
- timeline-shard: 4
- entry-store: 3


# Absolute Dependence of the Service (ADS)

## Description
Number of other services a service depends on

## Interpretation
Value range: [0,13], lower is better

## Results

- mine: 2
- fanout: 4
- entry-cache: 0
- index: 0
- follow: 6
- timeline0: 2
- home: 3
- timeline1: 2
- search: 2
- post: 4
- reserve: 0
- api: 2
- timeline-shard: 0
- entry-store: 3


# Absolute Criticality of the Service (ACS)

## Description
Product of its absolute importance (AIS) and absolute dependence (ADS)

## Interpretation
Value range: [0,169], lower is better

## Results

- mine: 0
- fanout: 4
- entry-cache: 0
- index: 0
- follow: 30
- timeline0: 8
- home: 0
- timeline1: 8
- search: 0
- post: 4
- reserve: 0
- api: 0
- timeline-shard: 0
- entry-store: 9


# Number of Services (NS)

## Description
Number of services in the system

## Interpretation
Value range: [0,INF), lower is better

## Result
14


# Relative Coupling of Service (RCS)

## Description
Degree to which a service depends on the other services

## Interpretation
Value range: [0,100), lower is better

## Results

- mine: 14%
- fanout: 28%
- entry-cache: 0%
- index: 0%
- follow: 43%
- timeline0: 14%
- home: 21%
- timeline1: 14%
- search: 14%
- post: 28%
- reserve: 0%
- api: 14%
- timeline-shard: 0%
- entry-store: 21%


# Relative Importance of Service (RIS)

## Description
Degree to which the other services depend on a service

## Interpretation
Value range: [0,100), lower is better

## Results

- mine: 0%
- fanout: 7%
- entry-cache: 21%
- index: 14%
- follow: 36%
- timeline0: 28%
- home: 0%
- timeline1: 28%
- search: 0%
- post: 7%
- reserve: 21%
- api: 0%
- timeline-shard: 28%
- entry-store: 21%


# Service Coupling Factor (SCF)

## Description
Indicates the overall coupling in the system, the sum over all single coupling values is set in relation with the maximum couplings that could occur in the system

## Interpretation
Value range: [0,1], lower is better

## Result
0.16


# Weighted Service Interface Count (WSIC)

## Description
Weighted number of exposed interfaces or operations per service (all weights = 1.0)

## Interpretation
Value range: [0,INF), lower is better

## Results

- mine: 0.0
- fanout: 2.0
- entry-cache: 3.0
- index: 3.0
- follow: 2.0
- timeline0: 2.0
- home: 0.0
- timeline1: 2.0
- search: 0.0
- post: 1.0
- reserve: 2.0
- api: 0.0
- timeline-shard: 4.0
- entry-store: 2.0


# Service Composition Pattern (SCP)

## Description
Fraction of services which are composite

## Interpretation
Value range: [0,100], lower is better

## Result
71%


# Inverse of Average Number of Used Message (IAUM)

## Description
Ratio between number of services and message types

## Interpretation
Value range: (0,1], very low values should be avoided

## Result
0.61


# Service Interface Usage Cohesion (SIUC)

## Description
Quantifies cohesion of a service based on the number of operations invoked by every client

## Interpretation
Value range: (0,100], higher is better

## Results

- timeline1: 50%
- fanout: 100%
- post: 100%
- reserve: 100%
- entry-cache: 67%
- index: 50%
- timeline-shard: 75%
- follow: 50%
- entry-store: 50%
- timeline0: 50%


# Service Interface Data Cohesion (SIDC)

## Description
Quantifies cohesion of a service based on the number of operations sharing the same input parameter types

## Interpretation
Value range: [0,100], higher is better

## Results

- timeline1: 100%
- fanout: 0%
- post: 100%
- reserve: 100%
- entry-cache: 67%
- index: 67%
- timeline-shard: 100%
- follow: 100%
- entry-store: 100%
- timeline0: 100%


# Response for Operation (RFO)

## Description
Cardinality of the set of other service interfaces that can be executed in response to invocations with all possible parameters

## Interpretation
Value range: [0,INF), lower is better

## Results

- timeline1:
    - \[timeline:insert\]: 0
    - \[timeline:list\]: 1

- fanout:
    - \[fanout:entry\]: 1
    - \[info:entry\]: 1

- post:
    - \[post:entry\]: 1

- reserve:
    - \[reserve:remove\]: 0
    - \[reserve:create\]: 0

- entry-cache:
    - \[kind:entry, store:save\]: 0
    - \[kind:entry, store:list\]: 0
    - \[cache:true, kind:entry, store:\*\]: 0

- index:
    - \[info:entry\]: 0
    - \[search:insert\]: 0
    - \[search:query\]: 0

- timeline-shard:
    - \[timeline:insert\]: 0
    - \[shard:0, timeline:\*\]: 1
    - \[shard:1, timeline:\*\]: 1
    - \[timeline:list\]: 1

- follow:
    - \[follow:list\]: 2
    - \[follow:user\]: 2

- entry-store:
    - \[kind:entry, store:save\]: 1
    - \[kind:entry, store:list\]: 0

- timeline0:
    - \[timeline:insert\]: 0
    - \[timeline:list\]: 1

## Details

- entry-store (\[kind:entry, store:save\]): \[info:entry\]
- timeline1 (\[timeline:list\]): \[follow:\*\]
- post (\[post:entry\]): \[kind:entry,store:\*\]
- timeline-shard (\[shard:1, timeline:\*\]): \[follow:\*\]
- timeline-shard (\[shard:0, timeline:\*\]): \[follow:\*\]
- timeline-shard (\[timeline:list\]): \[follow:\*\]
- fanout (\[info:entry\]): \[follow:\*\]
- fanout (\[fanout:entry\]): \[follow:\*\]
- follow (\[follow:list\]): \[timeline:\*, search:\*\]
- follow (\[follow:user\]): \[kind:entry,store:\*, timeline:\*\]
- timeline0 (\[timeline:list\]): \[follow:\*\]


# Total Response for Service (TRS)

## Description
Sum of RFO values for all operations

## Interpretation
Value range: [0,INF), lower is better

## Result
13


# Mean Absolute Importance/Dependence in the System (MAIDS)

## Description
Mean number of services which depend on a service / Mean number of services a service depends on

## Interpretation
Value range: [0,13], lower is better

## Result
2.14


# Mean Absolute Coupling in the System (MACS)

## Description
Mean number of services which depend on a service or a service depends on

## Interpretation
Value range: [0,26], lower is better

## Result
4.29


# Dynamic Relative Dependence of Service (DRDS)

## Description
Degree to which a service uses other services in terms of calls

## Interpretation
Value range: (0,100], higher is better

## Results

- mine:
    - entry-cache: 78%
    - entry-store: 22%

- timeline1:
    - reserve: 6%
    - follow: 94%

- search:
    - index: 50%
    - follow: 50%

- fanout:
    - timeline1: 14%
    - timeline-shard: 46%
    - follow: 24%
    - timeline0: 16%

- post:
    - fanout: 28%
    - entry-cache: 28%
    - index: 28%
    - entry-store: 14%

- api:
    - post: 76%
    - follow: 24%

- follow:
    - timeline1: 14%
    - reserve: 9%
    - entry-cache: 12%
    - timeline-shard: 50%
    - entry-store: 5%
    - timeline0: 11%

- entry-store:
    - timeline1: 26%
    - timeline-shard: 67%
    - timeline0: 8%

- timeline0:
    - reserve: 13%
    - follow: 87%

- home:
    - timeline1: 18%
    - timeline-shard: 67%
    - timeline0: 15%


# Dynamic Relative Importance of Service (DRIS)

## Description
Degree to which other services depend on a service in terms of calls

## Interpretation
Value range: (0,100], higher is better

## Results

- timeline1:
    - fanout: 18%
    - follow: 18%
    - entry-store: 24%
    - home: 40%

- fanout:
    - post: 100%

- post:
    - api: 100%

- reserve:
    - timeline1: 13%
    - follow: 63%
    - timeline0: 25%

- entry-cache:
    - mine: 10%
    - post: 72%
    - follow: 18%

- index:
    - search: 54%
    - post: 46%

- timeline-shard:
    - fanout: 18%
    - follow: 20%
    - entry-store: 19%
    - home: 44%

- follow:
    - timeline1: 21%
    - search: 39%
    - fanout: 17%
    - api: 5%
    - timeline0: 17%

- entry-store:
    - mine: 6%
    - post: 79%
    - follow: 15%

- timeline0:
    - fanout: 27%
    - follow: 19%
    - entry-store: 10%
    - home: 44%


# Dynamic Relative Dependence of Service in the System (DRDSS)

## Description
Degree to which a service uses other services in terms of calls compared to all calls system-wide

## Interpretation
Value range: (0,100], lower is better

## Results

- mine:
    - entry-cache: 1%
    - entry-store: 0%

- timeline1:
    - reserve: 0%
    - follow: 4%

- search:
    - index: 7%
    - follow: 7%

- fanout:
    - timeline1: 2%
    - timeline-shard: 6%
    - follow: 3%
    - timeline0: 2%

- post:
    - fanout: 6%
    - entry-cache: 6%
    - index: 6%
    - entry-store: 3%

- api:
    - post: 3%
    - follow: 1%

- follow:
    - timeline1: 2%
    - reserve: 1%
    - entry-cache: 1%
    - timeline-shard: 6%
    - entry-store: 1%
    - timeline0: 1%

- entry-store:
    - timeline1: 2%
    - timeline-shard: 6%
    - timeline0: 1%

- timeline0:
    - reserve: 0%
    - follow: 3%

- home:
    - timeline1: 4%
    - timeline-shard: 14%
    - timeline0: 3%


# Dynamic Relative Importance of Service in the System (DRISS)

## Description
Degree to which other services depend on a service in terms of calls compared to all calls system-wide

## Interpretation
Value range: (0,100], lower is better

## Results

- timeline1:
    - fanout: 2%
    - follow: 2%
    - entry-store: 2%
    - home: 4%

- fanout:
    - post: 6%

- post:
    - api: 3%

- reserve:
    - timeline1: 0%
    - follow: 1%
    - timeline0: 0%

- entry-cache:
    - mine: 1%
    - post: 6%
    - follow: 1%

- index:
    - search: 7%
    - post: 6%

- timeline-shard:
    - fanout: 6%
    - follow: 6%
    - entry-store: 6%
    - home: 14%

- follow:
    - timeline1: 4%
    - search: 7%
    - fanout: 3%
    - api: 1%
    - timeline0: 3%

- entry-store:
    - mine: 0%
    - post: 3%
    - follow: 1%

- timeline0:
    - fanout: 2%
    - follow: 1%
    - entry-store: 1%
    - home: 3%


# Cyclic Service Dependencies (CSD)

## Description
Number of cycles in the dependencies

## Interpretation
Value range: [0,18348340022], lower is better

## Result
2

## Details

- entry-store --> timeline1 --> follow --> entry-store
- entry-store --> timeline0 --> follow --> entry-store
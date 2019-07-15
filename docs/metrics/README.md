# Service-Based Maintainability Metrics

We collected a set of 58 service-based maintainability metrics proposed in scientific literature. From these, we chose 16 candidates and adapted an additional 7 metrics for not covered parts (Authors = Schlinger et al.). All in all, we then implemented 23 metrics for the prototype. The metrics are listed in the table below and in [docs/metrics/maintainability-metrics.csv](maintainability-metrics.csv).

| Metric Name                                            | Abbreviation | Layer     | Design Property | Authors               | Source | Applicable to MS | Collectable at Runtime | Selected |
| ------------------------------------------------------ | ------------ | --------- | --------------- | --------------------- | -----: | ---------------: | ---------------------: | -------: |
| Number of Services Involved in the Compound Service    | NSIC         | Service   | Complexity      | Rud et al.            |    [7] |              yes |                    yes |      yes |
| Services Interdependence in the System                 | SIY          | System    | Coupling        | Rud et al.            |    [7] |              yes |                    yes |      yes |
| Absolute Importance of the Service                     | AIS          | Service   | Coupling        | Rud et al.            |    [7] |              yes |                    yes |      yes |
| Absolute Dependence of the Service                     | ADS          | Service   | Coupling        | Rud et al.            |    [7] |              yes |                    yes |      yes |
| Absolute Criticality of the Service                    | ACS          | Service   | Coupling        | Rud et al.            |    [7] |              yes |                    yes |      yes |
| Response for Operation                                 | RFO          | Operation | Complexity      | Perepletchikov et al. |    [5] |              yes |                    yes |      yes |
| Total Response for Service                             | TRS          | Service   | Complexity      | Perepletchikov et al. |    [5] |              yes |                    yes |      yes |
| Service Interface Data Cohesion                        | SIDC         | Service   | Cohesion        | Perepletchikov et al. |    [4] |              yes |                    yes |      yes |
| Service Interface Usage Cohesion                       | SIUC         | Service   | Cohesion        | Perepletchikov et al. |    [4] |              yes |                    yes |      yes |
| Relative Coupling of Service                           | RCS          | Service   | Coupling        | Qingqing et al.       |    [6] |              yes |                    yes |      yes |
| Relative Importance of Service                         | RIS          | Service   | Coupling        | Qingqing et al.       |    [6] |              yes |                    yes |      yes |
| Service Coupling Factor                                | SCF          | System    | Coupling        | Qingqing et al.       |    [6] |              yes |                    yes |      yes |
| Weighted Service Interface Count                       | WSIC         | Service   | Size            | Hirzalla et al.       |    [1] |              yes |                    yes |      yes |
| Service Composition Pattern                            | SCP          | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                    yes |      yes |
| Inverse of Average Number of Used Message              | IAUM         | System    | Cohesion        | Shim et al.           |    [3] |              yes |                    yes |      yes |
| Number of Services                                     | NS           | System    | Size            | Shim et al.           |    [3] |              yes |                    yes |      yes |
| Mean Absolute Importance/Dependence in the System      | MAIDS        | System    | Coupling        | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| Mean Absolute Coupling in the System                   | MACS         | System    | Coupling        | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| Dynamic Relative Dependence of Service                 | DRDS         | Service   | Coupling        | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| Dynamic Relative Importance of Service                 | DRIS         | Service   | Coupling        | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| Dynamic Relative Dependence of Service in the System   | DRDSS        | Service   | Coupling        | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| Dynamic Relative Importance of Service in the System   | DRISS        | Service   | Coupling        | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| Cyclic Service Dependencies                            | CSD          | System    | Complexity      | Schlinger et al.      |    --- |              yes |                    yes |      yes |
| System's CentraliZation                                | SCZ          | System    | Centralization  | Hofmeister et al.     |    [2] |               no |                    yes |       no |
| Weighted Intra-Service Coupling between Elements       | WISCE        | Element   | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| Weighted Extra-Service Incoming Coupling of an Element | WESICE       | Element   | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| Weighted Extra-Service Outgoing Coupling of an Element | WESOCE       | Element   | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| Extra-Service Incoming Coupling of Service Interface   | ESICSI       | Service   | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| Element to Extra Service Interface Outgoing Coupling   | EESIOC       | Element   | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| Service Interface to Intra Element Coupling            | SIIEC        | Service   | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| System Partitioning Factor                             | SPARF        | System    | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| System Purity Factor                                   | SPURF        | System    | Coupling        | Perepletchikov et al. |    [5] |              yes |                     no |       no |
| Service Sequential Usage Cohesion                      | SSUC         | Service   | Cohesion        | Perepletchikov et al. |    [4] |              yes |                     no |       no |
| Strict Service Implementation Cohesion                 | SSIC         | Service   | Cohesion        | Perepletchikov et al. |    [4] |              yes |                     no |       no |
| Loose Service Implementation Cohesion                  | LSIC         | Service   | Cohesion        | Perepletchikov et al. |    [4] |              yes |                     no |       no |
| Total Interface Cohesion of a Service                  | TICS         | Service   | Cohesion        | Perepletchikov et al. |    [4] |              yes |                     no |       no |
| Service Granularity                                    | SG           | Service   | Complexity      | Qingqing et al.       |    [6] |              yes |                    yes |       no |
| Service Coupling Factor                                | SCF          | System    | Coupling        | Hofmeister et al.     |    [2] |              yes |                    yes |       no |
| System's Service Coupling                              | SSC          | System    | Coupling        | Hofmeister et al.     |    [2] |              yes |                    yes |       no |
| Extent of Aggregation                                  | EOA          | System    | Complexity      | Hofmeister et al.     |    [2] |              yes |                    yes |       no |
| Density of Aggregation                                 | DOA          | System    | Complexity      | Hofmeister et al.     |    [2] |              yes |                    yes |       no |
| Aggregator CentraliZation                              | ACZ          | System    | Centralization  | Hofmeister et al.     |    [2] |               no |                    yes |       no |
| Stateless Services                                     | SS           | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                     no |       no |
| Service Support for Transactions                       | SST          | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                     no |       no |
| Service Realization Pattern                            | SRP          | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                     no |       no |
| Number of Services                                     | NOS          | System    | Size            | Hirzalla et al.       |    [1] |              yes |                    yes |       no |
| Service Access Method                                  | SAM          | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                     no |       no |
| Dynamic vs. Static Service Selection                   | DSSS         | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                     no |       no |
| Number of Versions per Service                         | NOVS         | System    | Complexity      | Hirzalla et al.       |    [1] |              yes |                     no |       no |
| Average Number of Directly Connected Services          | ADCS         | System    | Coupling        | Shim et al.           |    [8] |              yes |                     no |       no |
| Number of Operations                                   | NO           | System    | Size            | Shim et al.           |    [8] |              yes |                    yes |       no |
| AVG # of Operations to AVG # of Messages               | AOMR         | System    | Size            | Shim et al.           |    [8] |              yes |                    yes |       no |
| Coarse-Grained Parameter Ratio                         | CPR          | System    | Size            | Shim et al.           |    [8] |              yes |                     no |       no |
| CoHesion at Message level                              | CHM          | System    | Cohesion        | Jin et al.            |    [3] |              yes |                     no |       no |
| CoHesion at Domain level                               | CHD          | System    | Cohesion        | Jin et al.            |    [3] |              yes |                     no |       no |
| InterFace Number                                       | IFN          | System    | Size            | Jin et al.            |    [3] |              yes |                    yes |       no |
| OPeration Number                                       | OPN          | System    | Size            | Jin et al.            |    [3] |              yes |                    yes |       no |
| InteRaction Number                                     | IRN          | Service   | Size            | Jin et al.            |    [3] |              yes |                    yes |       no |

## References

1. Hirzalla, M., Cleland-Huang, J., Arsanjani, A.: A Metrics Suite for Evaluating Flexibility and Complexity in Service Oriented Architectures. In: Krämer, B.J., Lin, K.-J., and Narasimhan, P. (eds.) Service-Oriented Computing -- ICSOC 2008 Workshops: ICSOC 2008 International Workshops, Sydney, Australia, December 1st, 2008, Revised Selected Papers. pp. 41–52. Springer Berlin Heidelberg, Berlin, Heidelberg (2009).
2. Hofmeister, H., Wirtz, G.: Supporting Service-Oriented Design with Metrics. In: 2008 12th International IEEE Enterprise Distributed Object Computing Conference. pp. 191–200. IEEE (2008).
3. Jin, W., Liu, T., Zheng, Q., Cui, D., Cai, Y.: Functionality-Oriented Microservice Extraction Based on Execution Trace Clustering. In: 2018 IEEE International Conference on Web Services (ICWS). pp. 211–218. IEEE (2018).
4. Perepletchikov, M., Ryan, C., Frampton, K.: Cohesion Metrics for Predicting Maintainability of Service-Oriented Software. In: Seventh International Conference on Quality Software (QSIC 2007). pp. 328–335. IEEE (2007).
5. Perepletchikov, M., Ryan, C., Frampton, K., Tari, Z.: Coupling Metrics for Predicting Maintainability in Service-Oriented Designs. In: 2007 Australian Software Engineering Conference (ASWEC’07). pp. 329–340. IEEE (2007).
6. Qingqing, Z., Xinke, L.: Complexity Metrics for Service-Oriented Systems. In: 2009 Second International Symposium on Knowledge Acquisition and Modeling. pp. 375–378. IEEE (2009).
7. Rud, D., Schmietendorf, A., Dumke, R.R.: Product Metrics for Service-Oriented Infrastructures. In: IWSM/MetriKon (2006).
8. Shim, B., Choue, S., Kim, S., Park, S.: A Design Quality Model for Service-Oriented Architecture. In: 2008 15th Asia-Pacific Software Engineering Conference. pp. 403–410. IEEE (2008).

## Newly Designed Metrics

The 7 metrics adapted by us are described in this section. Metric formalization is based on the following entities:

- The set of services *S* contains individual services, e.g. *s<sub>1</sub>, s<sub>2</sub> &isin; S*.
- The function *ID(s)* returns the number of incoming dependencies for *s*, i.e. the number of other services that depend on *s*.
- The function *OD(s)* returns the number of outgoing dependencies of *s*, i.e. the number of other services *s* depends on.
- The set *D(S)* contains all dependencies between services in *S*.
- The function *cycles(D)* returns the set of cyclic dependencies within *D*.
- The function *calls(s<sub>1</sub>, s<sub>2</sub>, t)* returns the number of calls from *s<sub>1</sub>* to *s<sub>2</sub>* within timeframe *t*. 
  - All incoming calls to *s<sub>1</sub>* within *t* are indicated by *calls(\*, s<sub>1</sub>, t)*
  - All outgoing calls from *s<sub>1</sub>* within *t* are indicated by *calls(s<sub>1</sub>, \*, t)*.
  - The total number of calls in *S* during *t* is indicated by *calls(\*, \*, t)*.

<br/><br/>

### Mean Absolute Importance/Dependence in the System (MAIDS)

This metric is a system-wide variant of AIS/ADS [7]. It returns the mean number of service dependencies a service in *S* has. At the system level, the mean number of incoming and outgoing dependencies is identical. The value range of MAIDS is [0, |*S*| - 1].

![MAIDS Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20MAIDS%28S%29%20%3D%20%5Cfrac%7B%20%5Csum%5Climits_%7Bs%20%5Cin%20S%7D%20ID%28s%29%20%7D%7B%20%7CS%7C%20%7D%20%3D%20%5Cfrac%7B%20%5Csum%5Climits_%7Bs%20%5Cin%20S%7D%20OD%28s%29%20%7D%7B%20%7CS%7C%20%7D)

### Mean Absolute Coupling in the System (MACS)

This metric is a system-wide variant of ACS [7]. It analyzes the degree of coupling in *S* by calculating the mean number of dependencies from both sides, incoming as well as outgoing. The value range of MACS is [0, 2 \* (|*S*| - 1)].

![MACS Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20MACS%28S%29%20%3D%20%5Cfrac%7B%20%5Csum%5Climits_%7Bs%20%5Cin%20S%7D%20%5CBig%28%20ID%28s%29%20+%20OD%28s%29%20%5CBig%29%20%7D%7B%20%7CS%7C%20%7D%20%3D%202%20*%20MAIDS%28S%29)

### Dynamic Relative Dependence of Service (DRDS)

This is a dynamic variant of the metric RCS [6]. It describes how strongly *s<sub>1</sub>* is coupled to *s<sub>2</sub>* based on the percentage of outgoing calls from *s<sub>1</sub>* that went to *s<sub>2</sub>* within timeframe *t*. The value range of DRDS is [0, 1].

![DRDS Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20DRDS%28s_1%2C%20s_2%2C%20t%29%20%3D%20%5Cfrac%7B%20calls%28s_1%2C%20s_2%2C%20t%29%20%7D%7B%20calls%28s_1%2C%20*%2C%20t%29%20%7D)

### Dynamic Relative Importance of Service (DRIS)

This metric is a dynamic variant of RIS [6] and therefore the complement to DRDS. It describes how strongly *s<sub>1</sub>* is coupled to *s<sub>2</sub>* based on the percentage of incoming calls to *s<sub>1</sub>* that originated from *s<sub>2</sub>* within timeframe *t*. The value range of DRIS is [0, 1].

![DRIS Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20DRIS%28s_1%2C%20s_2%2C%20t%29%20%3D%20%5Cfrac%7B%20calls%28s_2%2C%20s_1%2C%20t%29%20%7D%7B%20calls%28*%2C%20s_1%2C%20t%29%20%7D)

### Dynamic Relative Dependence of Service in the System (DRDSS)

This metric is a system-wide variant of DRDS. It describes how strongly *s<sub>1</sub>* is coupled to *s<sub>2</sub>* based on the percentage calls from *s<sub>1</sub>* to *s<sub>2</sub>* take in the complete system *S* within timeframe *t*. The value range of DRDSS is [0, 1].

![DRDSS Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20DRDSS%28s_1%2C%20s_2%2C%20t%29%20%3D%20%5Cfrac%7B%20calls%28s_1%2C%20s_2%2C%20t%29%20%7D%7B%20calls%28*%2C%20*%2C%20t%29%20%7D)

### Dynamic Relative Importance of Service in the System (DRISS)

This metric is a system-wide variant of DRIS and therefore the complement to DRDSS. It describes how strongly *s<sub>1</sub>* is coupled to *s<sub>2</sub>* based on the percentage calls from *s<sub>2</sub>* to *s<sub>1</sub>* take in the complete system within timeframe *t*. The value range of DRISS is [0, 1].

![DRISS Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20DRISS%28s_1%2C%20s_2%2C%20t%29%20%3D%20%5Cfrac%7B%20calls%28s_2%2C%20s_1%2C%20t%29%20%7D%7B%20calls%28*%2C%20*%2C%20t%29%20%7D)

### Cyclic Service Dependencies (CSD)

This system-wide metric returns the number of cyclic dependency chains within *S*. While the metric SIY [7] only returns the number of bi-directional dependencies between two services, CSD includes cycles involving more than two services. The value range for CSD is [0, &infin;].

![CSD Formula](https://latex.codecogs.com/svg.latex?%5Clarge%20CSD%28S%29%20%3D%20%7C%20cycles%28D%28S%29%29%20%7C)

# healthcare-integration-platform

<h3>The health care project consists of six sub-projects and two utility projects.</h3>

<h4>(1) sub project</h4><br>
<h6>1) healthcare-task-dispatcher<br>The client's request is stored in the database. It decides which organization is to be allocated this request, and distributes the task at regular intervals.</h6><br>
<h6>2) healthcare-cdc-data-extractor<br>Middleware for extracting data from KCDC (Korea Centers for Disease Control & Prevention) organization. It provides the function to extract data corresponding to user's request such as KCDC's community health survey, national health nutrition survey, and genome data.</h6><br>
<h6>3) healthcare-nhic-data-extractor<br>Middleware for extracting data from NHIC (National Health Insurence Center) organization. It provides the function to extract data corresponding to user's request such as NHIC's disease, treatment materials, medical activities, medicine and patient standard data.</h6><br>
<h6>4) healthcare-hira-data-extractor<br>Middleware for extracting data from HIRA (Health Insurence Review & Assessment service) organization. It is based on patient, hospitalized patient, hospitalized patient, pediatric and elderly patient data based on patient and specification standards such as diseases, And provides a function of extracting data corresponding to a user's request.</h6><br>
<h6>5) healthcare-kihasa-data-extractor<br>Middleware for extracting data from KIHASA(Korea Institute for Health and Social Affairs) organization. It provides the function to extract data corresponding to user's request according to meaningful indicators based on KIHASA's medical panel data.</h6><br>
<h6>6) healthcare-scenario-processor<br>It provides data extraction function for WorkFlow UI.</h6><br>

<h4>(2) utility</h4><br>
<h6>1) healthcare-meta-data-refinery</h6><br>
<h6>2) hive-table-maker</h6><br>

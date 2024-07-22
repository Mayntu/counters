const postCounter = async () => {
    let response = await fetch(
        "http://localhost:8080/counter",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "name": "testName",
                "groupName": "testGroup",
            }),
        },
    );
    let text = await response.text();
    console.log(text);
};

// postCounter();


const postCounterGroup = async () => {
    let response = await fetch(
        "http://localhost:8080/counterGroup",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "name": "testGroup",
            }),
        },
    );
    let text = await response.text();
    console.log(text);
};

// postCounterGroup();

const postCounterReading = async () => {
    let response = await fetch(
        "http://localhost:8080/counterReading",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "counterId": 1,
                "groupId": 1,
                "date": "25.07",
                "currentReading": 100.0,
            }),
        },
    );
    let text = await response.text();
    console.log(text);
};

// postCounterReading();

const getCounterReading = async () => {
    let response = await fetch(
        "http://localhost:8080/counterReading?id=10",
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        },
    ).then((response) => response.text());
    console.log(JSON.parse(response));
}

// getCounterReading();

const getAllCounterReadings = async () => {
    let response = await fetch(
        "http://localhost:8080/counterReading/all",
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        },
    ).then((response) => response.text())
    console.log(JSON.parse(response));
}

// getAllCounterReadings();


const getCounters = async () => {
    let response = await fetch(
        "http://localhost:8080/countersData",
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        },
    ).then((response) => response.text())
    return JSON.parse(response);
}

const viewData = async () => {
    let data = await getCounters();
console.log(data);


let groupedData = data.reduce((acc, element) => {
    let groupName = element.groupName;
    if (!acc[groupName]) {
        acc[groupName] = [];
    }
    acc[groupName].push(element);
    return acc;
}, {});


Object.keys(groupedData).forEach(groupName => {
    let groupData = groupedData[groupName];

    let table = document.createElement("table");

    let tableHGroup = document.createElement("th");
    let tableHMin = document.createElement("th");
    let tableHMax = document.createElement("th");
    let tableHAvg = document.createElement("th");

    let tableRowHeader = document.createElement("tr");

    tableHGroup.textContent = "Группа";
    tableHMin.textContent = "Мин показание";
    tableHMax.textContent = "Макс показание";
    tableHAvg.textContent = "Расход";

    tableRowHeader.appendChild(tableHGroup);
    tableRowHeader.appendChild(tableHMin);
    tableRowHeader.appendChild(tableHMax);
    tableRowHeader.appendChild(tableHAvg);

    table.appendChild(tableRowHeader);

    groupData.forEach(element => {
        let counterData = element.counterData;

        let tableRow = document.createElement("tr");

        let tableRGroup = document.createElement("td");
        let tableRMin = document.createElement("td");
        let tableRMax = document.createElement("td");
        let tableRAvg = document.createElement("td");

        tableRGroup.textContent = groupName;
        tableRMin.textContent = counterData.min;
        tableRMax.textContent = counterData.max;
        tableRAvg.textContent = counterData.avg;

        tableRow.appendChild(tableRGroup);
        tableRow.appendChild(tableRMin);
        tableRow.appendChild(tableRMax);
        tableRow.appendChild(tableRAvg);

        table.appendChild(tableRow);
    });

    document.body.appendChild(table);
});
};

viewData();
const fetchCars = document.getElementById('btn-fetch-cars');
const fetchMembers = document.getElementById('btn-fetch-members');
const tableBody = document.querySelector('#table-body');
const urlCars = 'api/cars';
const urlMembers = 'api/members';

fetchCars.addEventListener('click', () => {
    fetch(urlCars)
        .then(response => response.json())
        .then(cars => {
            const rowsAsStr = cars.map(car => {
                return `<tr>
                        <td>${car.brand}</td>
                        <td>${car.model}</td>
                        <td>${car.pricePrDay}</td>
                    </tr>`
            }).join('');
            document.getElementById('tbl-head').innerHTML = `<tr>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Price Pr Day</th>
                    </tr>`;
            document.getElementById("tbl-body").innerHTML = rowsAsStr;
        });
});

fetchMembers.addEventListener('click', () => {
    fetch(urlMembers)
        .then(response => response.json())
        .then(members => {
            const rowsAsStr = members.map(member => {
                return `<tr>
                        <td>${member.username}</td>
                        <td>${member.email}</td>
                        <td>${member.firstName}</td>
                    </tr>`
            }).join('');
            document.getElementById('tbl-head').innerHTML = `<tr>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Firstname</th>
                    </tr>`;
            document.getElementById("tbl-body").innerHTML = rowsAsStr;
        });
});
const showAddCarFormButton = document.getElementById('btn-show-add-car-form');
const addCarForm = document.getElementById('add-car-form');

showAddCarFormButton.addEventListener('click', () => {
    // Toggle the visibility of the form
    if (addCarForm.style.display === 'none') {
        addCarForm.style.display = 'block';
    } else {
        addCarForm.style.display = 'none';
    }
});

addCarForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const brand = document.getElementById('brand').value;
    const model = document.getElementById('model').value;
    const rentalPriceDay = parseFloat(document.getElementById('rentalPriceDay').value);
    const maxDiscount = parseInt(document.getElementById('maxDiscount').value);

    const carData = {
        brand,
        model,
        rentalPriceDay,
        maxDiscount,
    };

    // Send a POST request to add the car
    fetch('/api/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(carData),
    })
        .then((response) => response.json())
        .then((car) => {
            // Clear the form
            addCarForm.reset();

            // Optionally, update the cars table with the new car data
            // You can reuse the existing code for displaying cars here

            // Alert the user that the car was added successfully
            alert('Car added successfully');
        })
        .catch((error) => {
            console.error('Error adding car:', error);
            alert('An error occurred while adding the car');
        });
});
    const userId = retrieveUserId();
    fetch(`/Pantry/${userId}`)
            .then(response => response.json())
        .then(pantryItems => {
            const pantryList = document.getElementById('pantry-list');
            pantryItems.forEach(item => {
                const listItem = document.createElement('li');
                listItem.innerHTML = `
  <strong>Item Name:</strong> ${item.itemName} <br>
  <strong>Category:</strong> ${item.category} <br>
  <strong>Expiry Date:</strong> ${item.expiryDate} <br>
`;
                pantryList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching pantry items:', error));

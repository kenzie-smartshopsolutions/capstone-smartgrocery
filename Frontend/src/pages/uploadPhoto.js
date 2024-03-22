import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PetClient from "../api/PetClient";
import axios from "axios";

/**
 * Logic needed for the view playlist page of the website.
 */
class PawfectMatch extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderPet'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('search-pet-form').addEventListener('submit', this.onGet);
        document.getElementById('add-pet-form').addEventListener('submit', this.handleFormSubmit.bind(this));
        document.getElementById('update-pet-form').addEventListener('submit', this.updatePet.bind(this));
        document.getElementById('delete-pet-form').addEventListener('submit', this.handleDeleteFormSubmit.bind(this));
        this.client = new PetClient();

        this.dataStore.addChangeListener(this.renderPet)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderPet() {
        let resultArea = document.getElementById("pet-profile");

        const petData = this.dataStore.get("PetData");
        console.log(petData); // Debug line

        if (petData) {
            resultArea.innerHTML = `
                <div>ID: ${petData.id}</div>
                <div>Name: ${petData.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    async renderPetProfile() {
        const petData = this.dataStore.get('PetData');
        console.log(petData); // Debug line

        if (petData) {
            const profileDiv = document.getElementById('pet-profile');
            profileDiv.innerHTML = `
    <p>Pet ID: ${petData.petId}</p>
      <h3>${petData.name}</h3>
      <img src="${petData.imageUrl}" alt="${petData.name}">
      <p>Age: ${petData.age}</p>
      <p>Pet Type: ${petData.petType}</p>
    `;
            profileDiv.classList.remove('hidden');
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        console.log('onGet called'); // Debug line

        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("pet-id-field").value;
        this.dataStore.set("PetData", null);

        const result = await this.client.getPet(id, this.errorHandler);
        console.log(result); // Debug line

        if (result) {
            this.dataStore.set("PetData", result);
            this.showMessage(`Got ${result.name}!`)
            this.renderPetProfile();
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async uploadImageToCloudinary(imageFile) {
        const formData = new FormData()

        // Check if imageFile exists. If not, console.warn and return null
        if (!imageFile) {
            console.warn('No image file was provided for upload.');
            return null;
        }

        formData.append('file', imageFile);
        formData.append('upload_preset', 'vpxux2lw');

        try {
            const response = await axios.post('https://api.cloudinary.com/v1_1/dehjkkblr/image/upload', formData);
            return response.data.url; // The image URL on Cloudinary
        } catch (error) {
            console.error('Error uploading image:', error);
            console.error('Error data:', error.response && error.response.data);
            return null;
        }
    }

    // // Function to handle form submission
    async getFormData() {
        // Get the image file from a file input field
        const imageFile = document.getElementById('pet-image-field').files[0];

        let imageUrl;
        try {
            imageUrl = await this.uploadImageToCloudinary(imageFile);
        } catch (error) {
            console.error('Error uploading image:', error);
        }

        // Get other pet details from form fields
        const name = document.getElementById('pet-name-field').value;
        const petType = document.getElementById('pet-type-field').value;
        const age = parseInt(document.getElementById('pet-age-field').value, 10);
        return { name, petType, age, imageUrl };
    }

    async handleFormSubmit(event) {
        event.preventDefault();

        const petData = await this.getFormData();
        const createdPet = await this.client.createPet(petData, this.errorHandler);
        if (createdPet) {
            this.showMessage(`Created ID: ${createdPet.petId} for ${createdPet.name}!`);
            this.dataStore.set('PetData', createdPet);
        } else {
            this.errorHandler("Error creating pet. Please, try again.");
        }
    }
    async handleDeleteFormSubmit(event) {
        event.preventDefault();

        const petIdToDelete = document.getElementById('deletePet-id-field').value;
        const deletedSuccessfully = await this.client.deletePet(petIdToDelete);

        if (deletedSuccessfully) {
            // Clear the pet profile from the display if the currently displayed pet was deleted
            const currentPetData = this.dataStore.get('PetData');
            if (currentPetData && currentPetData.id === petIdToDelete) {
                this.dataStore.set('PetData', null);
            }

            this.showMessage(`Pet with ID ${petIdToDelete} has been deleted`);
        } else {
            this.errorHandler('Error deleting pet. Please ensure that you entered a valid ID.');
        }
    }

    async onCreate(event) {
        event.preventDefault();
        console.log('onCreate called'); // Debug line
        this.dataStore.set("PetData", null);

        const name = document.getElementById("create-name-field").value;
        const petType = document.getElementById("pet-type-field").value;
        const age = parseInt(document.getElementById("pet-age-field").value, 10);
        const imageFile = document.getElementById("pet-image-field").files[0];
        const imageUrl = await this.uploadImageToCloudinary(imageFile);

        const petData = {name, petType, age, imageUrl};
        const createdPet = await this.client.createPet(petData, this.errorHandler);

        console.log(createdPet); // Debug line

        if (createdPet) {
            this.showMessage(`Created ID: ${createdPet.petId} for ${createdPet.name}!`);
            this.dataStore.set('PetData', createdPet);
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
    async updatePet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Fetch form elements and get updated values
        const petId = document.getElementById('updatePet-id-field').value;
        const newName = document.getElementById('updatePet-name-field').value;
        const newAge = parseInt(document.getElementById('updatePet-age-field').value, 10);

        // Create the updatedPetData object with only name and age
        const updatedPetData = { name: newName, age: newAge };

        // Invoke the updatePet method in the client with the petId and updatedPetData
        // I'm assuming the updatePet method in your client takes the petId
        // and the data to update as arguments and returns the updated pet data
        const response = await this.client.updatePet(petId, updatedPetData);

        // If the pet data is successfully updated, save and render the updated pet
        if (response) {
            this.showMessage(`Updated pet: ID ${petId}, new name "${newName}", new age ${newAge}`);
            this.dataStore.set('PetData', response);
            this.renderPetProfile();
        } else {
            this.errorHandler("Error updating pet. Please try again.");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const pawfectMatch = new PawfectMatch();
    await pawfectMatch.mount();
    console.log(pawfectMatch); // Debug line
};

window.addEventListener('DOMContentLoaded', main);
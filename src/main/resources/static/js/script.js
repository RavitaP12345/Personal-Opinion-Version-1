// script.js
function toggleEditedDetails() {
    var editedDetails = document.getElementById('edited-details');
    if (editedDetails.style.display === 'none') {
        editedDetails.style.display = 'block';
    } else {
        editedDetails.style.display = 'none';
    }
}
function toggleEditedDetailsV1(id) {
    var editedDetails = document.getElementById(id);
    if (editedDetails.style.display === 'none') {
        editedDetails.style.display = 'block';
    } else {
        editedDetails.style.display = 'none';
    }
}

function deleteAccount() {
    // Implement delete account functionality here
    // For example, display a confirmation dialog and then delete the account
    if (confirm('Are you sure you want to delete your account?')) {
        // Delete account logic goes here
    }
  }
    
    
    
    
    
function showModal() {
            document.getElementById('deleteModal').style.display = 'block';
        }

function hideModal() {
            document.getElementById('deleteModal').style.display = 'none';
        }
        
        
        
function showPostModel() {
            document.getElementById('deleteModal').style.display = 'block';
        }

        function hidePostModal() {
            document.getElementById('deleteModal').style.display = 'none';
        }
        
        
        
        
function sendLikeDislike(postId, action) {
    fetch('/saveLikeDislike', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'postId': postId,
            'action': action
        })
    })
    
}





document.addEventListener('DOMContentLoaded', function() {
    var deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var userId = button.getAttribute('data-user-id');
            showModal(userId);
        });
    });
});

function showModalV1(userId) {
    var modal = document.getElementById('deleteModal');
    var confirmDeleteButton = document.getElementById('confirmDeleteButton');
    
    // Update the delete confirmation link
    confirmDeleteButton.href = '/deleteUserAccount?userId=' + userId;
    
    modal.style.display = 'block';
}

function hideModalV1() {
    var modal = document.getElementById('deleteModal');
    modal.style.display = 'none';
}

// Close the modal if the user clicks anywhere outside of the modal
window.onclick = function(event) {
    var modal = document.getElementById('deleteModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}



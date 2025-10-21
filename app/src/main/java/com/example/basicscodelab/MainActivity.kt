import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val DASHBOARD_ROUTE = "dashboard"
    const val ADDSTUDENT_ROUTE = "addstudent"
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClicked: (String, String) -> Unit = { _, _ -> }
) {
    // State untuk menyimpan input email dan password dari pengguna
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Input field untuk Email
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input field untuk Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(), // Menyembunyikan teks password
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Login
        Button(
            onClick = {
                // Panggil lambda onLoginClicked saat tombol ditekan
                onLoginClicked(username, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

data class Student(
    val ID: String,
    val Name: String,
    val Phone: String,
    val Address: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    students: List<Student> = emptyList(),
    onFabClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Roster") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Student")
            }
        }
    ) { innerPadding ->
        if (students.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No students yet.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(students) { student ->
                    StudentRow(student = student)
                }
            }
        }
    }
}

@Composable
fun StudentRow(student: Student, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = student.ID,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = student.Name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}


@Composable
fun AddStudentScreen(
    modifier: Modifier = Modifier,
    onSaveStudent: (Student) -> Unit = {}
) {
    // State untuk setiap input field
    var ID by remember { mutableStateOf("") }
    var Name by remember { mutableStateOf("") }
    var Phone by remember { mutableStateOf("") }
    var Address by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Add New Student",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = ID,
            onValueChange = { ID = it },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = Name,
            onValueChange = { Name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = Phone,
            onValueChange = { Phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = Address,
            onValueChange = { Address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val newStudent = Student(ID, Name, Phone, Address)
                onSaveStudent(newStudent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Student")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddStudentScreenPreview() {
    AddStudentScreen()
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Anda dapat membungkusnya dengan Tema Material jika Anda memilikinya
    // Misal: YourAppTheme {
    LoginScreen()
    // }
}

@Preview(showBackground = true, name = "Roster with Students")
@Composable
fun StudentRosterScreenWithStudentsPreview() {
    val sampleStudents = listOf(
        Student("101", "John Doe", Phone = "081234567890", Address = "123 Main St"),
        Student("102", "Jane Smith", Phone = "089876543210", Address = "456 Oak Ave"),
        Student("103", "Peter Jones", Phone = "081122334455", Address = "789 Pine Ln")
    )
    DashboardScreen(students = sampleStudents)
}

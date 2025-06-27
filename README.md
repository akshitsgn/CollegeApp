# 🏫 College Defaulter Management App

## 🔍 Project Overview  
This **College App** is designed to simplify and automate the **in-and-out tracking** of hostel students using a structured, multi-role system. Built with **Jetpack Compose** and **Firebase**, it ensures real-time record-keeping, accountability, and communication between guards, wardens, and administrators. The app helps improve hostel discipline and enhances operational transparency.

## 📱 App Screenshots

<p align="center">
  <img src="https://github.com/akshitsgn/CollegeApp/blob/main/a53d5a34-1b65-4313-9f4d-608a05a0170f.jpg" alt="Warden Dashboard" width="300"/>
  <img src="https://github.com/akshitsgn/CollegeApp/blob/main/67b6a070-5fc5-43bc-98fe-c1a497e320c2.jpg" width="300"/>
</p>

## ✨ Key Features

### 👥 Multi-User Role System
- **Admin**: Final authority; reviews escalated cases and takes disciplinary action.
- **Warden**: Intermediate authority; monitors hostel late entries and forwards severe cases.
- **Guard** (or Automated Entry): Logs late arrivals with details and image capture.

### 🚨 Defaulter Management
- **Auto Entry Logging**: When a student enters after hostel in-time, their details are captured:
  - 📸 Live photo using device camera  
  - 🆔 Registration number  
  - 🏠 Hostel name  
  - 👤 Student name  
  - 🕓 Date and time
- Stored in Firebase for real-time access and review.

### 🛡️ Warden Dashboard
- **Defaulter List View**: Review all students who violated in-time rules.
- **Actions**:
  - ✅ **Resolve Locally**: Remove defaulter if issue is justified.
  - 📤 **Forward to Admin**: Escalate critical cases.

### 🏛️ Admin Dashboard
- **Received Forwarded Cases**: View defaulters escalated by wardens.
- **Actions**:
  - ❌ **Mark as Resolved**: Close the case if no action needed.
  - ⚠️ **Take Disciplinary Action**: Proceed with appropriate steps based on incident severity.

## 🔁 Workflow

1. A student enters the hostel late.
2. The app captures their details and photo and logs them in the system.
3. The entry appears in the **Warden Dashboard**.
4. The **Warden** either:
   - Resolves it locally, or  
   - Forwards it to the **Admin**.
5. The **Admin** reviews and decides to close the case or take action.

## 📚 Technologies Used

- **Jetpack Compose** – UI toolkit for declarative design
- **Firebase (Realtime DB + Storage)** – backend and data sync
- **MVVM Architecture** – structured, scalable code
- **CameraX API** – live photo capture
- **Kotlin** – core development language

## 💼 Use Cases

- 🏠 **Hostel Management**: Track late arrivals with full transparency.
- 🧑‍🏫 **Warden Operations**: Provide structured decisions on minor offenses.
- 🧑‍💼 **Admin Oversight**: Receive only serious violations for final review and action.

## 🛠️ How to Run

1. Clone the repository:
```bash
git clone https://github.com/your-username/CollegeDefaulterApp

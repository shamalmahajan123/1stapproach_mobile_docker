#!/bin/bash

# Logging function
log() {
    echo "$(date +"%Y-%m-%d %H:%M:%S") - $1"
}

# Check if adb is installed
if ! command -v adb &> /dev/null; then
    log "Error: adb command not found. Please install Android SDK."
    exit 1
fi

# Check if the emulator is running
is_emulator_running() {
    adb devices | grep emulator | grep -v "offline" > /dev/null
    return $?
}

# Wait for the emulator to be ready
wait_for_emulator() {
    local timeout=300
    local start_time=$(date +%s)
    sleep 5
    adb devices
    log "Waiting for the emulator to be ready..."
    while true; do
        if is_emulator_running; then
            log "Emulator is ready."
            return 0
        fi

        sleep 5
        log "Checking emulator status..."
        local current_time=$(date +%s)
        if (( current_time - start_time > timeout )); then
            log "Error: Emulator did not start within the timeout period."
            exit 1
        fi
    done
}

# Set Katalon version
KATALON_VERSION="8.6.8"
KATALON_PATH="/app/Katalon_Studio_Engine_Linux_64-${KATALON_VERSION}"

# Run Katalon test cases
run_katalon_test_cases() {
    log "===> Running Katalon test cases <==="

    if ! cd "${KATALON_PATH}"; then
        log "Error: Failed to change directory to Katalon installation at ${KATALON_PATH}."
        exit 1
    fi

    ./katalonc -noSplash -runMode=console -projectPath="/app/BanReservas-Android-Personal.prj" \
        -retry=0 -testSuitePath="Test Suites/LOGIN/TS1_Login_SMS" \
        -browserType="Android" -deviceId="emulator-5554" -executionProfile="default" \
        -apiKey="529a7837-34f5-4076-90d8-a31db12875ae" \
        --config -proxy.auth.option=NO_PROXY -proxy.system.option=NO_PROXY \
        -proxy.system.applyToDesiredCapabilities=true \
        -timeout=600 || {
        log "Error: Katalon test execution failed."
        exit 2
    }
}

# Main function to run Katalon test cases
main() {
    log "===> SCRIPT FOR RUNNING KATALON TEST CASES <==="

    wait_for_emulator  # Ensure the emulator is ready before running tests
    run_katalon_test_cases
}

# Start the main function
main

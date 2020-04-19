package com.speelyaal.thaalam.datamodel.vm

enum class VirtualMachineStatus {
    running,
    offline,
    booting,
    rebooting,
    shuttingDown,
    provisioning,
    deleteing,
    migrating,
    rebuilding,
    cloning,
    restoring

}
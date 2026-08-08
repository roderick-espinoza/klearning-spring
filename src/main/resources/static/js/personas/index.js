/*
 * Un unico modal para crear y editar. Los valores de los <select> son el
 * name() del enum, y los data-* de la fila traen ese mismo name(), asi que
 * basta con asignarlos directamente para que el select quede preseleccionado.
 */

function abrirModalNuevo() {
    document.getElementById('modalPersonaTitulo').textContent = 'Nueva Persona';
    document.getElementById('formPersona').action = URL_PERSONAS;

    document.getElementById('inputApellidos').value = '';
    document.getElementById('inputNombres').value = '';
    document.getElementById('inputDni').value = '';
    document.getElementById('inputFechaNacimiento').value = '';
    document.getElementById('inputCelular').value = '';
    document.getElementById('inputEmail').value = '';
    document.getElementById('inputFechaIngreso').value = '';
    document.getElementById('inputFechaCese').value = '';

    document.getElementById('selectSexo').value = '';
    document.getElementById('selectEstadoCivil').value = '';
    document.getElementById('selectNacionalidad').value = '';
    document.getElementById('selectFormacion').value = '';
    document.getElementById('selectModalidad').value = '';
    document.getElementById('selectSkill').value = '';
}

function abrirModalEditar(btn) {
    document.getElementById('modalPersonaTitulo').textContent = 'Editar Persona';
    document.getElementById('formPersona').action = URL_PERSONAS + '/' + btn.dataset.id;

    document.getElementById('inputApellidos').value = btn.dataset.apellidos;
    document.getElementById('inputNombres').value = btn.dataset.nombres;
    document.getElementById('inputDni').value = btn.dataset.dni;
    document.getElementById('inputFechaNacimiento').value = btn.dataset.fechanacimiento;
    document.getElementById('inputCelular').value = btn.dataset.celular;
    document.getElementById('inputEmail').value = btn.dataset.email;
    document.getElementById('inputFechaIngreso').value = btn.dataset.fechaingreso;
    document.getElementById('inputFechaCese').value =
        btn.dataset.fechacese && btn.dataset.fechacese !== 'null'
            ? btn.dataset.fechacese
            : '';

    document.getElementById('selectSexo').value = btn.dataset.sexo;
    document.getElementById('selectEstadoCivil').value = btn.dataset.estadocivil;
    document.getElementById('selectNacionalidad').value = btn.dataset.nacionalidad;
    document.getElementById('selectFormacion').value = btn.dataset.formacion;
    document.getElementById('selectModalidad').value = btn.dataset.modalidad;
    document.getElementById('selectSkill').value = btn.dataset.skill;
}

function confirmarEliminar(e) {
    e.preventDefault();
    const form = e.target;

    Swal.fire({
        title: '¿Eliminar persona?',
        text: 'El registro quedará marcado como eliminado.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then(result => {
        if (result.isConfirmed) form.submit();
    });

    return false;
}

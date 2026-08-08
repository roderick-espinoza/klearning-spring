/*
 * Mismo patron que el resto de CRUD: un unico modal que sirve para crear y
 * para editar. Lo unico que cambia entre ambos casos es el action del form,
 * porque crear va a POST /roles y editar a POST /roles/{id}.
 */

/*
 * El campo Codigo ya se veia en mayusculas por la clase text-uppercase, pero eso
 * es solo CSS: lo que viajaba al servidor era lo tecleado. Aqui se convierte de
 * verdad, para que lo que se ve y lo que se envia sean lo mismo.
 */
document.addEventListener('DOMContentLoaded', function () {
    const codigo = document.getElementById('inputCodigoRol');

    codigo.addEventListener('input', function () {
        const posicion = this.selectionStart;
        this.value = this.value.toUpperCase();
        this.setSelectionRange(posicion, posicion);
    });
});

function abrirModalNuevo() {
    document.getElementById('modalRolTitulo').textContent = 'Nuevo Rol';
    document.getElementById('formRol').action = URL_ROLES;

    document.getElementById('inputCodigoRol').value = '';
    document.getElementById('inputNombreRol').value = '';
    document.getElementById('inputDescripcionRol').value = '';
}

function abrirModalEditar(btn) {
    document.getElementById('modalRolTitulo').textContent = 'Editar Rol';
    document.getElementById('formRol').action = URL_ROLES + '/' + btn.dataset.id;

    document.getElementById('inputCodigoRol').value = btn.dataset.codigo;
    document.getElementById('inputNombreRol').value = btn.dataset.nombre;
    document.getElementById('inputDescripcionRol').value = btn.dataset.descripcion;
}

function confirmarEliminar(e) {
    e.preventDefault();
    const form = e.target;

    Swal.fire({
        title: '¿Eliminar rol?',
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

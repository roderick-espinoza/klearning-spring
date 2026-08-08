/*
 * A diferencia del resto de CRUD, aqui el rol no es un select simple sino uno
 * multiple sobre la tabla usuarios_roles. Por eso al editar no vale asignar
 * select.value: hay que recorrer las opciones y marcar las que esten en el
 * data-roles de la fila.
 *
 * La contraseña tampoco se precarga nunca: dejarla vacia al editar significa
 * "no cambiarla", y asi lo interpreta UsuarioServiceImpl.
 */

function abrirModalNuevo() {
    document.getElementById('modalUsuarioTitulo').textContent = 'Nuevo Usuario';
    document.getElementById('formUsuario').action = URL_USUARIOS;

    document.getElementById('selectPersona').value = '';
    document.getElementById('inputUsuario').value = '';

    const password = document.getElementById('inputPassword');
    password.value = '';
    password.required = true;
    document.getElementById('ayudaPassword').textContent = 'Mínimo 8 caracteres.';

    marcarRoles([]);
}

function abrirModalEditar(btn) {
    document.getElementById('modalUsuarioTitulo').textContent = 'Editar Usuario';
    document.getElementById('formUsuario').action = URL_USUARIOS + '/' + btn.dataset.id;

    document.getElementById('selectPersona').value = btn.dataset.persona;
    document.getElementById('inputUsuario').value = btn.dataset.usuario;

    const password = document.getElementById('inputPassword');
    password.value = '';
    password.required = false;
    document.getElementById('ayudaPassword').textContent =
        'Déjala en blanco para mantener la contraseña actual.';

    const idsRoles = (btn.dataset.roles || '')
        .split(',')
        .map(id => id.trim())
        .filter(id => id.length > 0);

    marcarRoles(idsRoles);
}

function marcarRoles(idsRoles) {
    const select = document.getElementById('selectRoles');

    for (const opcion of select.options) {
        opcion.selected = idsRoles.includes(opcion.value);
    }
}

function confirmarEliminar(e) {
    e.preventDefault();
    const form = e.target;

    Swal.fire({
        title: '¿Eliminar usuario?',
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
